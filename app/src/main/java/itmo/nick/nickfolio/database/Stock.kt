package itmo.nick.nickfolio.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Stock(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "symbol") val symbol: String?,
    @ColumnInfo(name = "sector") val sector: String?,
    @ColumnInfo(name = "price_2023") val price2023: String?,
    @ColumnInfo(name = "price_2022") val price2022: String?,
    @ColumnInfo(name = "price_2021") val price2021: String?,
    @ColumnInfo(name = "price_2020") val price2020: String?,
    @ColumnInfo(name = "price_2019") val price2019: String?,
    @ColumnInfo(name = "price_2018") val price2018: String?,
    @ColumnInfo(name = "price_2017") val price2017: String?,
    @ColumnInfo(name = "price_2016") val price2016: String?,
    @ColumnInfo(name = "price_2015") val price2015: String?,
    @ColumnInfo(name = "price_2014") val price2014: String?,
    @ColumnInfo(name = "price_2013") val price2013: String?,
    @ColumnInfo(name = "dividend_2023") val dividend2023: String?,
    @ColumnInfo(name = "dividend_2022") val dividend2022: String?,
    @ColumnInfo(name = "dividend_2021") val dividend2021: String?,
    @ColumnInfo(name = "dividend_2020") val dividend2020: String?,
    @ColumnInfo(name = "dividend_2019") val dividend2019: String?,
    @ColumnInfo(name = "dividend_2018") val dividend2018: String?,
    @ColumnInfo(name = "dividend_2017") val dividend2017: String?,
    @ColumnInfo(name = "dividend_2016") val dividend2016: String?,
    @ColumnInfo(name = "dividend_2015") val dividend2015: String?,
    @ColumnInfo(name = "dividend_2014") val dividend2014: String?,
    @ColumnInfo(name = "dividend_2013") val dividend2013: String?,
    @ColumnInfo(name = "currency") val currency: String?
)