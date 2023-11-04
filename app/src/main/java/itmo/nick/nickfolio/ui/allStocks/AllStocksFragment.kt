package itmo.nick.nickfolio.ui.allStocks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val allStocksViewModel =
            ViewModelProvider(this).get(AllStocksViewModel::class.java)

        _binding = FragmentAllStocksBinding.inflate(inflater, container, false)
        val root: View = binding.root

       // val textView: TextView = binding.textGallery
        allStocksViewModel.text.observe(viewLifecycleOwner) {
           // textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}