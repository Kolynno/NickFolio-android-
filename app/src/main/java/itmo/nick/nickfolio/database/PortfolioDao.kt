package itmo.nick.nickfolio.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PortfolioDao {
    @Insert
    fun insert(vararg portfolio: Portfolio)

    @Query("SELECT name FROM Portfolio")
    fun getAllNames(): List<String>

    @Delete
    fun delete(portfolio: Portfolio)
}