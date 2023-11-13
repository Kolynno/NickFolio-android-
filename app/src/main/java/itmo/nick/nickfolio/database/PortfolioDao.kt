package itmo.nick.nickfolio.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PortfolioDao {
    @Insert
    fun insert(vararg portfolio: Portfolio)
    @Query("SELECT name FROM Portfolio")
    fun getAllNames(): List<String>
    @Query("SELECT * FROM Portfolio WHERE name = :name")
    fun getPortfolioByName(name: String): Portfolio
    @Update
    fun update(portfolio: Portfolio)
    @Delete
    fun delete(portfolio: Portfolio)
}