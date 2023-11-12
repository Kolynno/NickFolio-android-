package itmo.nick.nickfolio.analyze

class Analyze {

    companion object {
         fun stockBest(years: Int): String {
            when(years) {
                5 -> return "1,2,3,4"
                10 -> return "5,6,7,8,9,10"
            }
            return ""
        }

         fun stockDivid(years: Int): String {
            when(years) {
                5 -> return "1,2,3"
                10 -> return "4,5,6,7,8"
            }
            return ""
        }

        fun stockGrow(years: Int): String {
            when(years) {
                5 -> return "1,2"
                10 -> return "3,4,5,6"
            }
            return ""
        }
    }
}