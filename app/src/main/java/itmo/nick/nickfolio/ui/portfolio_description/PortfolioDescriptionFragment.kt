package itmo.nick.nickfolio.ui.portfolio_description

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import itmo.nick.nickfolio.R
import itmo.nick.nickfolio.database.OfferDatabase
import itmo.nick.nickfolio.database.PortfolioDatabase
import itmo.nick.nickfolio.database.StockDatabase
import itmo.nick.nickfolio.databinding.FragmentOfferDescriptionBinding
import itmo.nick.nickfolio.databinding.FragmentPortfolioBinding
import itmo.nick.nickfolio.databinding.FragmentPortfolioDescriptionBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PortfolioDescriptionFragment : Fragment() {
    private var _binding: FragmentPortfolioDescriptionBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPortfolioDescriptionBinding.inflate(inflater,container,false)
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

                val ids = portfolioRepository.getStocksIdsByName(requireArguments().getString("portfolioName").toString())
                if(ids != "") {
                    val stocks: List<String> = ids.split(",")
                    val stocksNames: List<String> = stocks.map {
                        stockRepository.getNameById(it.toInt())
                    }

                    val adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_list_item_1,
                        stocksNames
                    )
                    portfolioStockList.adapter = adapter
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}