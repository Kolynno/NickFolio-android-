package itmo.nick.nickfolio.portfolio_operations

import itmo.nick.nickfolio.database.OfferDao
import itmo.nick.nickfolio.database.PortfolioDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PortfolioOperations {
    companion object{
        fun addToPortfolio(portfolioName: String, portfolioRepository: PortfolioDao, offerRepository: OfferDao, offerName: String, ) {
            runBlocking {
                launch(Dispatchers.IO) {
                    val portfolio = portfolioRepository.getPortfolioByName(portfolioName)
                    val stocksIds = offerRepository.getStocksIdsByName(offerName)
                    portfolio.stocksIds = stocksIds
                    portfolioRepository.update(portfolio)
                }
            }
        }
    }
}