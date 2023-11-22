package itmo.nick.nickfolio.portfolio_operations

import itmo.nick.nickfolio.database.OfferDao
import itmo.nick.nickfolio.database.PortfolioDao
import itmo.nick.nickfolio.database.StockDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PortfolioOperations {

    /**
        Добавление новых акций в определенный портфель. Первая функция для добавления преложенных
        акций. Вторая - для одной акции из списка.
        Аргументы:
        portfolioName - название портфеля
        portfolioRepository - репозиторий портфелей
        offerRepository - репозиторий предложений
        offerName - название предложения
        stockRepository - репозиторий акций
        stockName - название акции
     */
    companion object{
        fun addToPortfolio(portfolioName: String, portfolioRepository: PortfolioDao,
                           offerRepository: OfferDao, offerName: String) {
            runBlocking {
                launch(Dispatchers.IO) {
                    val portfolio = portfolioRepository.getPortfolioByName(portfolioName)
                    val currentStocksIds =
                        portfolio.stocksIds?.
                        split(",")?.
                        filter { it.isNotEmpty() }?.
                        toMutableSet()
                    val newStocksIds =
                        offerRepository.getStocksIdsByName(offerName).split(",")

                    currentStocksIds?.addAll(newStocksIds)
                    portfolio.stocksIds = currentStocksIds?.joinToString(",")
                    portfolioRepository.update(portfolio)
                }
            }
        }

        fun addToPortfolio(portfolioName: String, portfolioRepository: PortfolioDao,
                           stockRepository: StockDao, stockName: String) {
            runBlocking {
                launch(Dispatchers.IO) {
                    val portfolio = portfolioRepository.getPortfolioByName(portfolioName)
                    val currentStocksIds = portfolio.stocksIds?.
                    split(",")?.
                    filter { it.isNotEmpty() }?.
                    toMutableSet()

                    val newStockId = stockRepository.getIdByName(stockName)
                    currentStocksIds?.add(newStockId.toString())
                    portfolio.stocksIds = currentStocksIds?.joinToString(",")
                    portfolioRepository.update(portfolio)
                }
            }
        }
    }
}