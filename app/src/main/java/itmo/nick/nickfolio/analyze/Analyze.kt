package itmo.nick.nickfolio.analyze
import android.app.Application
import android.util.Log
import itmo.nick.nickfolio.database.Stock


class Analyze(val application: Application) {

    companion object {

         fun stockBest(years: Int, stocks: List<Stock>): String {
            when(years) {
                5 -> return "1,2,3,4"
                10 -> return "5,6,7,8,9,10"
            }
            return ""
        }

         fun stockDivid(years: Int, stocks: List<Stock>): String {
            when(years) {
                5 -> return "1,2,3"
                10 -> return "4,5,6,7,8"
            }
            return ""
        }

        fun stockGrow(years: Int, stocks: List<Stock>): String {

            val stocksTop: MutableList<Pair<Int, Double>> = mutableListOf()

            for (stock in stocks) {

                val nowPrice = stock.price2023?.toDoubleOrNull()
                val lastPrice = stock.price2013?.toDoubleOrNull()


                if (nowPrice != null && lastPrice != null && lastPrice != 0.0) {
                    val diff = nowPrice - lastPrice
                    Log.v("TESTING", "diff:$diff, id:${stock.uid}")
                    val pair = Pair(stock.uid, (diff/lastPrice)*100 )
                    stocksTop.add(pair)
                } else {

                }
            }

            val sortedStocksTop = stocksTop.sortedByDescending { it.second }
            Log.v("TESTING", sortedStocksTop.toString())

            val sorted = sortedStocksTop.take(10).joinToString { it.first.toString() }.replace(" ", "")
            Log.v("TESTING", sorted)
            return sorted


        }
    }
}