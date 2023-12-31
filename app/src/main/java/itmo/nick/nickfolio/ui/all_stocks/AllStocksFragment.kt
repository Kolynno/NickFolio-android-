package itmo.nick.nickfolio.ui.all_stocks

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import itmo.nick.nickfolio.MainActivity
import itmo.nick.nickfolio.database.StockDatabase
import itmo.nick.nickfolio.databinding.FragmentAllStocksBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class AllStocksFragment : Fragment() {

    private var _binding: FragmentAllStocksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllStocksBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val stockList = binding.stockList

        val db = StockDatabase.getDatabaseStock(requireContext().applicationContext)
        val stockRepository = db.stockDao()

        runBlocking {
            launch(Dispatchers.IO) {
                val names = stockRepository.getAllNames()
                val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, names)
                stockList.adapter = adapter
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val stockList = binding.stockList

        stockList.setOnItemClickListener { _, _, position, _ ->
            if (activity is MainActivity) {
                val stockName = stockList.getItemAtPosition(position).toString()
                (activity as MainActivity).showStockDescriptionFragment(stockName)
            }
        }
    }
}