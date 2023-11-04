package itmo.nick.nickfolio.ui.stockOffer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import itmo.nick.nickfolio.databinding.FragmentStockOfferBinding

class StockOfferFragment : Fragment() {

    private var _binding: FragmentStockOfferBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val stockOfferViewModel =
            ViewModelProvider(this).get(StockOfferViewModel::class.java)

        _binding = FragmentStockOfferBinding.inflate(inflater, container, false)
        val root: View = binding.root


        stockOfferViewModel.text.observe(viewLifecycleOwner) {

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}