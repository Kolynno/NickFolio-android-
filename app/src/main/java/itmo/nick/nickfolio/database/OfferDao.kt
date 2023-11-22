package itmo.nick.nickfolio.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OfferDao {

    @Insert
    fun insert(vararg offer: Offer)
    @Query("SELECT name FROM Offer")
    fun getAllNames(): List<String>
    @Query("SELECT stocks_ids FROM Offer WHERE name=:name")
    fun getStocksIdsByName(name: String): String
    @Delete
    fun delete(offer: Offer)


}