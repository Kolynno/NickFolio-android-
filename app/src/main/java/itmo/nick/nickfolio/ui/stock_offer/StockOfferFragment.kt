package itmo.nick.nickfolio.ui.stock_offer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import itmo.nick.nickfolio.databinding.FragmentStockOfferBinding

class StockOfferFragment : Fragment() {

    private var _binding: FragmentStockOfferBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val StockOfferViewModel = ViewModelProvider(this).get(StockOfferViewModel::class.java)

        _binding = FragmentStockOfferBinding.inflate(inflater, container, false)
        val root: View = binding.root


        StockOfferViewModel.text.observe(viewLifecycleOwner) {
            // Обновите данные в адаптере здесь, когда они доступны
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
