package itmo.nick.nickfolio.database

import androidx.lifecycle.LiveData
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

    /*
        @Delete
        fun delete(stock: Stock)


         */
}