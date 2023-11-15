package itmo.nick.nickfolio.ui.offer_description

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import itmo.nick.nickfolio.MainActivity
import itmo.nick.nickfolio.database.OfferDatabase
import itmo.nick.nickfolio.database.StockDatabase
import itmo.nick.nickfolio.databinding.FragmentOfferDescriptionBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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

        val offerStockList = binding.offerStockList

        val offerDb = OfferDatabase.getDatabaseOffer(requireContext().applicationContext)
        val offerRepository = offerDb.offerDao()

        val stockDb = StockDatabase.getDatabaseStock(requireContext().applicationContext)
        val stockRepository = stockDb.stockDao()

        runBlocking {
            launch(Dispatchers.IO) {

                val ids = offerRepository.getStocksIdsByName(requireArguments().getString("offerName").toString())
                val stocks: List<String> = ids.split(",")

                val stocksNames: List<String> = stocks.map {
                    stockRepository.getNameById(it.toInt())
                }

                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, stocksNames)
                offerStockList.adapter = adapter
            }
        }

        val buttonToPortfolio = binding.buttonToPortfolio
        buttonToPortfolio.setOnClickListener{

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
