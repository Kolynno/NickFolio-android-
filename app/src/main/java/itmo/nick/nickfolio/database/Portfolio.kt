package itmo.nick.nickfolio.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Portfolio(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "stocks_ids") var stocksIds: String?
)
