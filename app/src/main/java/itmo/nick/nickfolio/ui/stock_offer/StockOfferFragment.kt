package itmo.nick.nickfolio.ui.stock_offer

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import itmo.nick.nickfolio.MainActivity
import itmo.nick.nickfolio.database.OfferDatabase
import itmo.nick.nickfolio.databinding.FragmentStockOfferBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class StockOfferFragment : Fragment() {

    private var _binding: FragmentStockOfferBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStockOfferBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val stockOfferList = binding.stockOfferList

        val db = OfferDatabase.getDatabaseOffer(requireContext().applicationContext)
        val offerRepository = db.offerDao()

        runBlocking {
            launch(Dispatchers.IO) {
                val names = offerRepository.getAllNames()
                val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, names)
                stockOfferList.adapter = adapter
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
        Переключение на другой фрагмент через MainActivity по клику на акцию
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val stockList = binding.stockOfferList

        stockList.setOnItemClickListener { _, _, position, _ ->
            if (activity is MainActivity) {
                val offerName = stockList.getItemAtPosition(position).toString()
                (activity as MainActivity).showOfferDescriptionFragment(offerName)
            }
        }
    }
}
