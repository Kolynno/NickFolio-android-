package itmo.nick.nickfolio.database
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.rules.TestRule

class OfferDaoTest {
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()
    private lateinit var offerDao: OfferDao
    private lateinit var database: OfferDatabase
    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            OfferDatabase::class.java
        ).allowMainThreadQueries().build()
        offerDao = database.offerDao()
    }
    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `insert and getAllNames should work correctly`() = runBlocking {
        val offer1 = Offer(uid = 1, name = "Offer1", stocksIds = "1,2,3")
        val offer2 = Offer(uid = 2, name = "Offer2", stocksIds = "4,5,6")
        offerDao.insert(offer1, offer2)
        val allOfferNames = offerDao.getAllNames()
        assert(allOfferNames.size == 2)
        assert(allOfferNames.contains("Offer1"))
        assert(allOfferNames.contains("Offer2"))
    }

    @Test
    fun `getStocksIdsByName should return correct stocksIds`() = runBlocking {
        val offer = Offer(uid = 3, name = "Offer3", stocksIds = "7,8,9")
        offerDao.insert(offer)
        val stocksIds = offerDao.getStocksIdsByName("Offer3")
        assertEquals("7,8,9", stocksIds)
    }

    @Test
    fun `delete should remove the offer`() = runBlocking {
        val offer = Offer(uid = 4, name = "OfferToDelete", stocksIds = "7,8,9")
        offerDao.insert(offer)
        offerDao.delete(offer)
        val stocksIds = offerDao.getStocksIdsByName("OfferToDelete")
        assert(stocksIds.isEmpty())
    }
}
