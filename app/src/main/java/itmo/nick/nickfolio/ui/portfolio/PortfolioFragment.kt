package itmo.nick.nickfolio.ui.portfolio

import android.R
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import itmo.nick.nickfolio.database.OfferDatabase
import itmo.nick.nickfolio.database.PortfolioDatabase
import itmo.nick.nickfolio.databinding.FragmentPortfolioBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PortfolioFragment : Fragment() {

    private var _binding: FragmentPortfolioBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val portfolioViewModel =
            ViewModelProvider(this).get(PortfolioViewModel::class.java)

        _binding = FragmentPortfolioBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val portfolioList = binding.portfolioList

        val db = PortfolioDatabase.getDatabasePortfolio(requireContext().applicationContext)
        val portfolioRepository = db.portfolioDao()

        runBlocking {
            launch(Dispatchers.IO) {
                val names = portfolioRepository.getAllNames()
                val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, names)
                portfolioList.adapter = adapter
            }
        }

        val buttonCreatePortfolio = binding.buttonCreatePortfolio

        buttonCreatePortfolio.setOnClickListener {
            createPortfolioDialog()
        }



        portfolioViewModel.text.observe(viewLifecycleOwner) {
        }
        return root
    }

    private fun createPortfolioDialog() {
        val builder = AlertDialog.Builder(requireContext().applicationContext)
        builder.setTitle("Новый портфель")

        val input = EditText(requireContext().applicationContext)
        input.hint = "Название"
        builder.setView(input)

        builder.setPositiveButton("OK") { dialog: DialogInterface?, _: Int ->
            val enteredText = input.text.toString().trim()
            if (enteredText.length >= 30) {
                Toast.makeText(requireContext().applicationContext, "Длина портфеля должна быть менее 30 символов!", Toast.LENGTH_SHORT).show()
            } else if (enteredText.isEmpty()) {
                Toast.makeText(requireContext().applicationContext, "Пустое имя!", Toast.LENGTH_SHORT).show()
            }
            dialog?.dismiss()
        }
        builder.setNegativeButton("Отмена") { dialog: DialogInterface?, _: Int ->
            dialog?.cancel()
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}