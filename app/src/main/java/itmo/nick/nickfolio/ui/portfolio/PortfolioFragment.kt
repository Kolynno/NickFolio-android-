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
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import itmo.nick.nickfolio.MainActivity
import itmo.nick.nickfolio.database.Portfolio
import itmo.nick.nickfolio.database.PortfolioDao
import itmo.nick.nickfolio.database.PortfolioDatabase
import itmo.nick.nickfolio.databinding.FragmentPortfolioBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class PortfolioFragment : Fragment() {

    private var _binding: FragmentPortfolioBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPortfolioBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val portfolioList = binding.portfolioList

        val db = PortfolioDatabase.getDatabasePortfolio(requireContext().applicationContext)
        val portfolioRepository = db.portfolioDao()

        runBlocking {
            launch(Dispatchers.IO) {
                val names = portfolioRepository.getAllNames()
                val adapter =
                    ArrayAdapter(requireContext(), R.layout.simple_list_item_1, names)
                portfolioList.adapter = adapter
            }
        }

        val buttonCreatePortfolio = binding.buttonCreatePortfolio
        buttonCreatePortfolio.setOnClickListener {
            createPortfolioDialog(portfolioRepository, portfolioList)
        }

        portfolioList.setOnItemLongClickListener {_, _, position, _ ->
            val positionText = portfolioList.getItemAtPosition(position).toString()
            editPortfolioDialog(portfolioRepository, portfolioList, positionText)
            true
        }
        return root
    }

    /**
        Создание диалогового окна для изменения названия акции или удаления
        Аргументы:
        portfolioRepository - репозиторий портфелей
        portfolioList - список акций портфеля
        positionText - название портфеля
     */
    private fun editPortfolioDialog(portfolioRepository: PortfolioDao, portfolioList: ListView, positionText: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(positionText)

        val input = EditText(requireContext())
        input.hint = "Изменить название"
        builder.setView(input)

        builder.setPositiveButton("OK") { dialog: DialogInterface?, _: Int ->
            val enteredText = input.text.toString().trim()
            if (enteredText != "") {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {

                        val oldPortfolio = portfolioRepository.getPortfolioByName(positionText)
                        oldPortfolio.name = enteredText
                        portfolioRepository.update(oldPortfolio)

                        val names = portfolioRepository.getAllNames()
                        withContext(Dispatchers.Main) {
                            val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, names)
                            portfolioList.adapter = adapter
                        }
                    }
                }
            }
            dialog?.dismiss()
        }
        builder.setNegativeButton("Отмена") { dialog: DialogInterface?, _: Int ->
            dialog?.cancel()
        }
        builder.setNeutralButton("Удалить") { dialog: DialogInterface?, _: Int ->
            lifecycleScope.launch(Dispatchers.IO) {

                    val oldPortfolio = portfolioRepository.getPortfolioByName(positionText)
                    portfolioRepository.delete(oldPortfolio)
                    val names = portfolioRepository.getAllNames()
                    withContext(Dispatchers.Main) {
                        val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, names)
                        portfolioList.adapter = adapter
                    }
            }
            dialog?.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    /**
     Создать диалоговое окно для создания нового портфеля.
     Аргументы:
     portfolioRepository - репозиторий портфелей
     portfolioList - список портфелей
     */
    private fun createPortfolioDialog(portfolioRepository: PortfolioDao, portfolioList: ListView) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Новый портфель")

        val input = EditText(requireContext())
        input.hint = "Название"
        builder.setView(input)

        builder.setPositiveButton("OK") { dialog: DialogInterface?, _: Int ->
            val enteredText = input.text.toString().trim()
            if (enteredText.length >= 30) {
                Toast.makeText(
                    requireContext(),
                    "Длина портфеля должна быть менее 30 символов!",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (enteredText.isEmpty()) {
                Toast.makeText(requireContext(), "Пустое имя!", Toast.LENGTH_SHORT).show()
            }
            else {
                lifecycleScope.launch(Dispatchers.IO) {
                    val newPortfolio = Portfolio(name = "Empty", stocksIds = "")
                    newPortfolio.name = enteredText
                    portfolioRepository.insert(newPortfolio)

                    val names = portfolioRepository.getAllNames()

                    launch(Dispatchers.Main) {
                        val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, names)
                        portfolioList.adapter = adapter
                    }
                }
            }
            dialog?.dismiss()
        }

        builder.setNegativeButton("Отмена") { dialog: DialogInterface?, _: Int ->
            dialog?.cancel()
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val portfolioList = binding.portfolioList

        portfolioList.setOnItemClickListener { _, _, position, _ ->
            if (activity is MainActivity) {
                val portfolioName = portfolioList.getItemAtPosition(position).toString()
                (activity as MainActivity).showPortfolioDescription(portfolioName)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}