package itmo.nick.nickfolio.ui.portfolio_description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import itmo.nick.nickfolio.database.PortfolioDao
import itmo.nick.nickfolio.database.PortfolioDatabase
import itmo.nick.nickfolio.database.StockDao
import itmo.nick.nickfolio.database.StockDatabase
import itmo.nick.nickfolio.databinding.FragmentPortfolioDescriptionBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class PortfolioDescriptionFragment : Fragment() {
    private var _binding: FragmentPortfolioDescriptionBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPortfolioDescriptionBinding.inflate(inflater, container, false)
        val view = binding.root

        val portfolioName = arguments?.getString("portfolioName")
        (activity as AppCompatActivity).supportActionBar?.title = portfolioName
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val portfolioStockList = binding.portfolioStockList

        val portfolioDb = PortfolioDatabase.getDatabasePortfolio(requireContext().applicationContext)
        val portfolioRepository = portfolioDb.portfolioDao()

        val stockDb = StockDatabase.getDatabaseStock(requireContext().applicationContext)
        val stockRepository = stockDb.stockDao()

        runBlocking {
            launch(Dispatchers.IO) {

                    val stocksNames = getStockNames(portfolioRepository, stockRepository)

                    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, stocksNames)
                    portfolioStockList.adapter = adapter
                }
        }

        /**
            Удаление акции из портфеля по долгому нажатию на нее.
            Также всплывает Toast для информировании пользователя об удалении акции
         */
        portfolioStockList.setOnItemLongClickListener { _, _, position, _ ->
            val stockName = portfolioStockList.getItemAtPosition(position).toString()
            var stocksIdsNow: MutableList<String> = mutableListOf()

            runBlocking {
                launch(Dispatchers.IO) {
                    val portfolioName = arguments?.getString("portfolioName")

                    val stockId = stockRepository.getIdByName(stockName)
                    val portfolio = portfolioRepository.getPortfolioByName(portfolioName.toString())
                    val currentStocksIds = portfolio.stocksIds?.split(",")?.toMutableList()

                    currentStocksIds?.remove(stockId.toString())
                    stocksIdsNow = currentStocksIds!!
                    portfolio.stocksIds = currentStocksIds.joinToString(",")

                    portfolioRepository.update(portfolio)
                }
            }

            Toast.makeText(
                requireContext(),
                "$stockName удалено",
                Toast.LENGTH_LONG
            ).show()

            requireActivity().runOnUiThread {

                val adapter = portfolioStockList.adapter as ArrayAdapter<String>
                adapter.clear()

                val stockNames = stocksIdsNow.map { stockId ->
                    runBlocking {
                        withContext(Dispatchers.IO) {
                            stockRepository.getNameById(stockId.toInt())
                        }
                    }
                }.toList()
                adapter.addAll(stockNames)
                adapter.notifyDataSetChanged()
            }
            true
        }
    }

     fun getStockNames(portfolioRepository: PortfolioDao, stockRepository: StockDao): List<String> {
        val ids = portfolioRepository.getStocksIdsByName(
            requireArguments().getString("portfolioName").toString()
        )
        if (ids != "") {
            val stocks: List<String> = ids.split(",")
            val stocksNames: List<String> = stocks.map {
                stockRepository.getNameById(it.toInt())
            }
            return stocksNames
        }
        return emptyList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}