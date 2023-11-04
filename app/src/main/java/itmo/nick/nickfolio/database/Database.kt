package itmo.nick.nickfolio.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Stock::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): StockDao
}