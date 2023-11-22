package itmo.nick.nickfolio.ui.portfolio_description

import io.mockk.coEvery
import io.mockk.mockk
import itmo.nick.nickfolio.database.PortfolioDao
import itmo.nick.nickfolio.database.StockDao
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class PortfolioDescriptionFragmentTest {

    @Test
    fun `should return correct data`() = runBlocking {

        val portfolioRepository = mockk<PortfolioDao>()
        val stockRepository = mockk<StockDao>()

        val portfolioName = "TestPortfolio"
        val expectedStockNames = listOf("Stock1", "Stock2", "Stock3")
        val portfolioIds = "1,2,3"

        coEvery { portfolioRepository.getStocksIdsByName(portfolioName) } returns portfolioIds
        coEvery { stockRepository.getNameById(any()) } answers {
            "Stock${firstArg<Int>()}"
        }

        val fragment = PortfolioDescriptionFragment()
        val result = fragment.getStockNames(portfolioRepository, stockRepository)

        assertEquals(expectedStockNames, result)
    }
}
