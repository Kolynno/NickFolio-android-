package itmo.nick.nickfolio.analyze
import android.app.Application
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
             val stocksTop: MutableList<Pair<Int, Int>> = mutableListOf()

             for (stock in stocks) {
                 var divid: Int? = 0

                 when(years) {
                     5 -> {
                         divid = (stock.dividend2023?.toIntOrNull() ?: 0) +
                                 (stock.dividend2022?.toIntOrNull() ?: 0) +
                                 (stock.dividend2021?.toIntOrNull() ?: 0) +
                                 (stock.dividend2020?.toIntOrNull() ?: 0) +
                                 (stock.dividend2019?.toIntOrNull() ?: 0) +
                                 (stock.dividend2018?.toIntOrNull() ?: 0)
                     }
                     10 -> {
                         divid = (stock.dividend2023?.toIntOrNull() ?: 0) +
                                 (stock.dividend2022?.toIntOrNull() ?: 0) +
                                 (stock.dividend2021?.toIntOrNull() ?: 0) +
                                 (stock.dividend2020?.toIntOrNull() ?: 0) +
                                 (stock.dividend2019?.toIntOrNull() ?: 0) +
                                 (stock.dividend2018?.toIntOrNull() ?: 0) +
                                 (stock.dividend2017?.toIntOrNull() ?: 0) +
                                 (stock.dividend2016?.toIntOrNull() ?: 0) +
                                 (stock.dividend2015?.toIntOrNull() ?: 0) +
                                 (stock.dividend2014?.toIntOrNull() ?: 0) +
                                 (stock.dividend2013?.toIntOrNull() ?: 0)
                     }
                 }
                 if (divid != null) {
                     val pair = Pair(stock.uid, divid)
                     stocksTop.add(pair)
                 }
             }
             val sortedStocksTop = stocksTop.sortedByDescending { it.second }
             return sortedStocksTop.take(10).joinToString { it.first.toString() }.replace(" ", "")
        }

        fun stockGrow(years: Int, stocks: List<Stock>): String {

            val stocksTop: MutableList<Pair<Int, Double>> = mutableListOf()

            for (stock in stocks) {
                var nowPrice: Double? = 0.0
                var lastPrice: Double? = 0.0

                when(years) {
                    5 -> {
                        nowPrice = stock.price2023?.toDoubleOrNull()
                        lastPrice = stock.price2018?.toDoubleOrNull()
                    }
                    10 -> {
                        nowPrice = stock.price2023?.toDoubleOrNull()
                        lastPrice = stock.price2013?.toDoubleOrNull()
                    }
                }

                if (nowPrice != null && lastPrice != null && lastPrice != 0.0) {
                    val diff = nowPrice - lastPrice
                    val pair = Pair(stock.uid, (diff / lastPrice) * 100)
                    stocksTop.add(pair)
                } else {

                }
            }
            val sortedStocksTop = stocksTop.sortedByDescending { it.second }
            return sortedStocksTop.take(10).joinToString { it.first.toString() }.replace(" ", "")
        }
    }
}