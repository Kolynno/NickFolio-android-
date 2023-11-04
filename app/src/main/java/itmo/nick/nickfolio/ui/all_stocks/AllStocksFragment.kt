package itmo.nick.nickfolio.ui.all_stocks

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import itmo.nick.nickfolio.databinding.FragmentAllStocksBinding


class AllStocksFragment : Fragment() {

    private var _binding: FragmentAllStocksBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, arrayListOf("1","2","3","4","5"))
        stockList.adapter = adapter


        AllStocksViewModel.text.observe(viewLifecycleOwner) {

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}