package itmo.nick.nickfolio.ui.stock_description

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import itmo.nick.nickfolio.database.PortfolioDao
import itmo.nick.nickfolio.database.PortfolioDatabase
import itmo.nick.nickfolio.database.StockDao
import itmo.nick.nickfolio.database.StockDatabase
import itmo.nick.nickfolio.databinding.FragmentStockDescriptionBinding
import itmo.nick.nickfolio.portfolio_operations.PortfolioOperations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StockDescriptionFragment : Fragment() {

    private var _binding: FragmentStockDescriptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStockDescriptionBinding.inflate(inflater, container, false)
        val view = binding.root

        val stockName = arguments?.getString("stockName")
        val stockSymbol = arguments?.getString("stockSymbol")
        val stockSector = arguments?.getString("stockSector")
        val stockPrice2023 = arguments?.getString("stockPrice2023")
        val stockDividends2023 = arguments?.getString("stockDividend2023")
        val stockCurrency = arguments?.getString("stockCurrency")


        binding.stockName.text = "Название: $stockName"
        binding.stockSymbol.text = "Код акции: $stockSymbol"
        binding.stockSector.text = "Сектор: $stockSector"
        if(stockCurrency == "RUB") {
            binding.stockPrice2023.text = "Цена на начало 2023 года: $stockPrice2023₽"
            binding.stockDividends2023.text = "Дивиденды за 2023 год: $stockDividends2023₽"
        } else {
            binding.stockPrice2023.text = "Цена на начало 2023 года: $stockPrice2023$"
            binding.stockDividends2023.text = "Дивиденды за 2023 год: $stockDividends2023$"
        }


        (activity as AppCompatActivity).supportActionBar?.title = stockName
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val portfolioDb = PortfolioDatabase.getDatabasePortfolio(requireContext().applicationContext)
        val portfolioRepository = portfolioDb.portfolioDao()

        val stockDb = StockDatabase.getDatabaseStock(requireContext().applicationContext)
        val stockRepository = stockDb.stockDao()

        val stockName = arguments?.getString("stockName")

        val buttonToPortfolio = binding.buttonAddStock
        buttonToPortfolio.setOnClickListener{
            showPortfolioSelectionDialog(portfolioRepository, stockRepository, stockName.toString())
        }
    }

    private fun showPortfolioSelectionDialog(
        portfolioRepository: PortfolioDao,
        stockRepository: StockDao,
        stockName: String
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
                            stockRepository,
                            stockName
                        )
                        dialog.dismiss()
                    }
                builder.create().show()
            }
        }
    }
}
