package itmo.nick.nickfolio.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert

@Dao
interface StockDao {



    @Insert
    fun insert(vararg stock: Stock)

    @Delete
    fun delete(stock: Stock)
}