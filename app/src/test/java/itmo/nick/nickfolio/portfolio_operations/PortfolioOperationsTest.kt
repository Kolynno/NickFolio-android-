package itmo.nick.nickfolio.portfolio_operations

import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import itmo.nick.nickfolio.database.OfferDao
import itmo.nick.nickfolio.database.PortfolioDao
import itmo.nick.nickfolio.database.StockDao
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.jupiter.api.Test

class PortfolioOperationsTest {

    private val testDispatcher = StandardTestDispatcher()
    @Test
    fun `addToPortfolio with offer should update portfolio correctly`() = testDispatcher.run {
        val portfolioRepository = mockk<PortfolioDao>()
        val offerRepository = mockk<OfferDao>()

        val portfolioName = "TestPortfolio"
        val offerName = "TestOffer"
        val newStockIds = "4,5,6"

        coEvery { portfolioRepository.getPortfolioByName(portfolioName) } returns mockk(relaxed = true)
        coEvery { portfolioRepository.update(any()) } returns Unit
        coEvery { offerRepository.getStocksIdsByName(offerName) } returns newStockIds

        PortfolioOperations.addToPortfolio(portfolioName, portfolioRepository, offerRepository, offerName)

        verify {
            portfolioRepository.getPortfolioByName(portfolioName)
            offerRepository.getStocksIdsByName(offerName)
            portfolioRepository.update(any())
        }
    }

    @Test
    fun `addToPortfolio with stock should update portfolio correctly`() = testDispatcher.run {
        val portfolioRepository = mockk<PortfolioDao>()
        val stockRepository = mockk<StockDao>()

        val portfolioName = "TestPortfolio"
        val stockName = "TestStock"
        val newStockId = "4"

        coEvery { portfolioRepository.getPortfolioByName(portfolioName) } returns mockk(relaxed = true)
        coEvery { portfolioRepository.update(any()) } returns Unit
        coEvery { stockRepository.getIdByName(stockName) } returns newStockId.toInt()

        PortfolioOperations.addToPortfolio(portfolioName, portfolioRepository, stockRepository, stockName)

        verify {
            portfolioRepository.getPortfolioByName(portfolioName)
            stockRepository.getIdByName(stockName)
            portfolioRepository.update(any())
        }
    }
}
