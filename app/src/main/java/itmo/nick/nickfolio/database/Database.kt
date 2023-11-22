package itmo.nick.nickfolio.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Stock::class], version = 1)
abstract class StockDatabase : RoomDatabase() {
    abstract fun stockDao(): StockDao


    /**
        Определение баз данных: Stock, Offer, Portfolio
     */
    companion object {
        @Volatile
        private var INSTANCE_STOCK: StockDatabase? = null

        fun getDatabaseStock(context: Context): StockDatabase {
            return INSTANCE_STOCK ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StockDatabase::class.java,
                    "Stock"
                ).build()
                INSTANCE_STOCK = instance

                instance
            }
        }
    }
}

@Database(entities = [Offer::class], version = 1)
abstract class OfferDatabase : RoomDatabase() {
    abstract fun offerDao(): OfferDao

    companion object {
        @Volatile
        private var INSTANCE_OFFER: OfferDatabase? = null

        fun getDatabaseOffer(context: Context): OfferDatabase {
            return INSTANCE_OFFER ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OfferDatabase::class.java,
                    "Offer"
                ).build()
                INSTANCE_OFFER = instance

                instance
            }
        }
    }
}

@Database(entities = [Portfolio::class], version = 1)
abstract class PortfolioDatabase : RoomDatabase() {
    abstract fun portfolioDao(): PortfolioDao
    companion object {
        @Volatile
        private var INSTANCE_OFFER: PortfolioDatabase? = null

        fun getDatabasePortfolio(context: Context): PortfolioDatabase {
            return INSTANCE_OFFER ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PortfolioDatabase::class.java,
                    "Portfolio"
                ).build()
                INSTANCE_OFFER = instance

                instance
            }
        }
    }
}
