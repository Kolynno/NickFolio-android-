package itmo.nick.nickfolio.ui.stock_offer

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import itmo.nick.nickfolio.MainActivity
import itmo.nick.nickfolio.databinding.FragmentStockOfferBinding

class StockOfferFragment : Fragment() {

    private var _binding: FragmentStockOfferBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val stockOfferViewModel = ViewModelProvider(this).get(StockOfferViewModel::class.java)

        _binding = FragmentStockOfferBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val stockOfferList = binding.stockOfferList

        val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, listOf("Лидеры роста за 5 лет","Лидеры роста за 10 лет"))
        stockOfferList.adapter = adapter

        stockOfferViewModel.text.observe(viewLifecycleOwner) {
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val stockList = binding.stockOfferList


        stockList.setOnItemClickListener { adapterView, view2, position, id ->
            if (activity is MainActivity) {
                val offerName = stockList.getItemAtPosition(position).toString()
                (activity as MainActivity).showOfferDescriptionFragment(offerName)
            }
        }



    }
}
