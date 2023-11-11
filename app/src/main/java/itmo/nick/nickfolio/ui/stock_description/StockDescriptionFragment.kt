package itmo.nick.nickfolio.ui.stock_description

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import itmo.nick.nickfolio.R
import itmo.nick.nickfolio.databinding.FragmentStockDescriptionBinding

class StockDescriptionFragment : Fragment() {

    private var _binding: FragmentStockDescriptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStockDescriptionBinding.inflate(inflater, container, false)
        val view = binding.root

        // Доступ к элементам разметки через binding
        val stockName = arguments?.getString("stockName")
        val stockSymbol = arguments?.getString("stockSymbol")
        val stockSector = arguments?.getString("stockSector")
        val stockPrice2023 = arguments?.getString("stockPrice2023")
        val stockDividends2023 = arguments?.getString("stockDividend2023")
        val stockCurrency = arguments?.getString("stockCurrency")

        binding.stockName.text = "Название акции:$stockName"
        binding.stockSymbol.text = "Код акции:$stockSymbol"
        binding.stockSector.text = "Сектор:$stockSector"
        binding.stockPrice2023.text = "Цена на начало 2023 года:$stockPrice2023"
        binding.stockDividends2023.text = "Дивиденды за 2023 год:$stockDividends2023"
        binding.stockCurrency.text = "Валюта акции:$stockCurrency"

        // Устанавливаем заголовок фрагмента
        (activity as AppCompatActivity).supportActionBar?.title = stockName

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
