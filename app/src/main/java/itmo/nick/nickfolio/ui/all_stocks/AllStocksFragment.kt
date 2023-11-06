package itmo.nick.nickfolio.ui.all_stocks

import android.R
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import itmo.nick.nickfolio.database.StockDatabase
import itmo.nick.nickfolio.databinding.FragmentAllStocksBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class AllStocksFragment : Fragment() {

    private var _binding: FragmentAllStocksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val AllStocksViewModel =
            ViewModelProvider(this).get(AllStocksViewModel::class.java)

        _binding = FragmentAllStocksBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val stockList = binding.stockList

        val db = StockDatabase.getDatabase(requireContext().applicationContext)
        val stockRepository = db.stockDao()

        runBlocking {
            launch(Dispatchers.IO) {
                val names = stockRepository.getAllNames()
                val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, names)
                stockList.adapter = adapter
            }
        }

        AllStocksViewModel.text.observe(viewLifecycleOwner) {
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}