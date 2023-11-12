package itmo.nick.nickfolio.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StockDao {

    @Query("SELECT name FROM Stock")
    fun getAllNames(): List<String>

    @Query("SELECT name FROM Stock WHERE uid=:id")
    fun getNameById(id: Int): String

    @Query("SELECT symbol FROM Stock WHERE name =:name ")
    fun getSymbolByName(name: String): String

    @Query("SELECT sector FROM Stock WHERE name =:name ")
    fun getSectorByName(name: String): String

    @Query("SELECT price_2023 FROM Stock WHERE name =:name ")
    fun getPrice2023ByName(name: String): String

    @Query("SELECT dividend_2023 FROM Stock WHERE name =:name ")
    fun getDividend2023ByName(name: String): String

    @Query("SELECT currency FROM Stock WHERE name =:name ")
    fun getCurrencyByName(name: String): String

    @Query("SELECT * FROM Stock")
    fun getAllStocks(): List<Stock>

    @Insert
    fun insert(vararg stock: Stock)

    @Delete
    fun delete(stock: Stock)
}