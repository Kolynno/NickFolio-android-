package itmo.nick.nickfolio.analyze
import android.app.Application
import android.util.Log
import itmo.nick.nickfolio.database.Stock


class Analyze() {

    companion object {

         fun stockBest(years: Int, stocks: List<Stock>): String {

             val stocksTop: MutableList<Pair<Int, Double>> = mutableListOf()

             for(stock in stocks) {
                 var totalProfit: Double
                 var nowPrice: Double? = 0.0
                 var lastPrice: Double? = 0.0

                 when (years) {
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
                     totalProfit = countDivid(years, stock).toDouble() + countGrowPercent(diff, lastPrice)
                     val pair = Pair(stock.uid, totalProfit)
                     stocksTop.add(pair)
                 }
             }

             val sortedStocksTop = stocksTop.sortedByDescending { it.second }
             return sortedStocksTop.take(10).joinToString { it.first.toString() }.replace(" ", "")
        }

         fun stockDivid(years: Int, stocks: List<Stock>): String {
             val stocksTop: MutableList<Pair<Int, Int>> = mutableListOf()

             for (stock in stocks) {
                 var divid: Int? = 0

                 when(years) {
                     5 -> {
                         divid = countDivid(years, stock)
                     }
                     10 -> {
                         divid = countDivid(years, stock)
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
        private fun countDivid(years: Int, stock: Stock): Int {
            when(years) {
                5 -> return  (stock.dividend2023?.toIntOrNull() ?: 0) +
                        (stock.dividend2022?.toIntOrNull() ?: 0) +
                        (stock.dividend2021?.toIntOrNull() ?: 0) +
                        (stock.dividend2020?.toIntOrNull() ?: 0) +
                        (stock.dividend2019?.toIntOrNull() ?: 0) +
                        (stock.dividend2018?.toIntOrNull() ?: 0)

                10 -> return (stock.dividend2023?.toIntOrNull() ?: 0) +
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
            return 0
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
                    val pair = Pair(stock.uid, countGrowPercent(diff, lastPrice))
                    stocksTop.add(pair)
                } else {

                }
            }
            val sortedStocksTop = stocksTop.sortedByDescending { it.second }
            return sortedStocksTop.take(10).joinToString { it.first.toString() }.replace(" ", "")
        }

        private fun countGrowPercent(diff: Double, lastPrice: Double): Double {
           return (diff / lastPrice) * 100
        }

    }
}