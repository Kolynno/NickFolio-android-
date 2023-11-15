package itmo.nick.nickfolio.portfolio_operations

import android.util.Log
import itmo.nick.nickfolio.database.OfferDao
import itmo.nick.nickfolio.database.PortfolioDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PortfolioOperations {
    companion object{
        fun addToPortfolio(portfolioName: String, portfolioRepository: PortfolioDao, offerRepository: OfferDao, offerName: String) {
            runBlocking {
                launch(Dispatchers.IO) {
                    val portfolio = portfolioRepository.getPortfolioByName(portfolioName)

                    val currentStocksIds = portfolio.stocksIds?.split(",")?.filter { it.isNotEmpty() }?.toMutableSet()

                    // Получаем новые акции для добавления
                    val newStocksIds = offerRepository.getStocksIdsByName(offerName).split(",")

                    // Добавляем только уникальные акции
                    currentStocksIds?.addAll(newStocksIds)

                    val endIds = currentStocksIds?.joinToString(",")

                    // Обновляем портфель с новыми акциями
                    portfolio.stocksIds = endIds
                    portfolioRepository.update(portfolio)
                }
            }
        }


    }
}