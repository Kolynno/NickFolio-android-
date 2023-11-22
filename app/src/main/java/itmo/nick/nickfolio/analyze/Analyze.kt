package itmo.nick.nickfolio.analyze
import itmo.nick.nickfolio.database.Stock


class Analyze {
    companion object {
        /**
            Составление списка 10 лучших акций по критериям: процент роста за 5/10 лет плюс
            процент дивидендной доходности за 5/10 лет.
            Все акции анализируются и возвращается строка вида "2,3,5,1,13,25,11,12,14,4",
            где представлены лучшие акции по критерию в порядке убывания
         */
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
                     totalProfit = countDividends(years, stock).toDouble() + countGrowPercent(diff, lastPrice)
                     val pair = Pair(stock.uid, totalProfit)
                     stocksTop.add(pair)
                 }
             }

             val sortedStocksTop = stocksTop.sortedByDescending { it.second }
             return sortedStocksTop.
             take(10).
             joinToString { it.first.toString() }.
             replace(" ", "")
        }

        /**
            Составление списка 10 лучших акций по критерию дивидендной доходности за 5/10 лет.
            Все акции анализируются и возвращается строка вида "2,3,5,1,13,25,11,12,14,4",
            где представлены лучшие акции по критерию в порядке убывания
         */
         fun stockDividends(years: Int, stocks: List<Stock>): String {
             val stocksTop: MutableList<Pair<Int, Int>> = mutableListOf()
             for (stock in stocks) {
                 var divid: Int? = 0

                 when(years) {
                     5 -> {
                         divid = countDividends(years, stock)
                     }
                     10 -> {
                         divid = countDividends(years, stock)
                     }
                 }
                 if (divid != null) {
                     val pair = Pair(stock.uid, divid)
                     stocksTop.add(pair)
                 }
             }
             val sortedStocksTop = stocksTop.sortedByDescending { it.second }
             return sortedStocksTop.
             take(10).
             joinToString { it.first.toString() }.
             replace(" ", "")
        }
        private fun countDividends(years: Int, stock: Stock): Int {
            when(years) {
                5 -> return (stock.dividend2023?.toIntOrNull() ?: 0) +
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

        /**
            Составление списка 10 лучших акций по критерию процент роста за 5/10 лет.
            Все акции анализируются и возвращается строка вида "2,3,5,1,13,25,11,12,14,4",
            где представлены лучшие акции по критерию в порядке убывания
         */
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
                }
            }
            val sortedStocksTop = stocksTop.sortedByDescending { it.second }
            return sortedStocksTop.
            take(10).
            joinToString { it.first.toString() }.
            replace(" ", "")
        }
        private fun countGrowPercent(diff: Double, lastPrice: Double): Double {
           return (diff / lastPrice) * 100
        }
    }
}