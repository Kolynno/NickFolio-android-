package itmo.nick.nickfolio.ui.offer_description

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import itmo.nick.nickfolio.database.OfferDao
import itmo.nick.nickfolio.database.OfferDatabase
import itmo.nick.nickfolio.database.PortfolioDao
import itmo.nick.nickfolio.database.PortfolioDatabase
import itmo.nick.nickfolio.database.StockDao
import itmo.nick.nickfolio.database.StockDatabase
import itmo.nick.nickfolio.databinding.FragmentOfferDescriptionBinding
import itmo.nick.nickfolio.portfolio_operations.PortfolioOperations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class OfferDescriptionFragment : Fragment() {
    private var _binding: FragmentOfferDescriptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOfferDescriptionBinding.inflate(inflater, container, false)
        val view = binding.root

        val offerName = arguments?.getString("offerName")

        (activity as AppCompatActivity).supportActionBar?.title = offerName
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val offerName = arguments?.getString("offerName")
        val offerStockList = binding.offerStockList

        val offerDb = OfferDatabase.getDatabaseOffer(requireContext().applicationContext)
        val offerRepository = offerDb.offerDao()

        val stockDb = StockDatabase.getDatabaseStock(requireContext().applicationContext)
        val stockRepository = stockDb.stockDao()

        val portfolioDb = PortfolioDatabase.getDatabasePortfolio(requireContext().applicationContext)
        val portfolioRepository = portfolioDb.portfolioDao()

        runBlocking {
            launch(Dispatchers.IO) {

                val stocksNames = getStockNames(portfolioRepository, stockRepository)

                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, stocksNames)
                offerStockList.adapter = adapter
            }
        }

        val buttonToPortfolio = binding.buttonToPortfolio
        buttonToPortfolio.setOnClickListener{
            showPortfolioSelectionDialog(portfolioRepository, offerRepository, offerName.toString())
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

    private fun showPortfolioSelectionDialog(
        portfolioRepository: PortfolioDao,
        offerRepository: OfferDao,
        offerName: String
    ) {
        lifecycleScope.launch(Dispatchers.IO) {
            val portfolios = portfolioRepository.getAllNames()

            withContext(Dispatchers.Main) {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Выберите портфель")
                    .setAdapter(
                        ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_list_item_1,
                            portfolios
                        )
                    ) { dialog: DialogInterface, which: Int ->

                        val selectedPortfolio = portfolios[which]
                        PortfolioOperations.addToPortfolio(
                            selectedPortfolio,
                            portfolioRepository,
                            offerRepository,
                            offerName
                        )
                        dialog.dismiss()
                    }
                builder.create().show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
