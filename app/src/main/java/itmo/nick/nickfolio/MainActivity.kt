package itmo.nick.nickfolio

import android.content.Context
import android.os.Bundle
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import itmo.nick.nickfolio.database.Stock
import itmo.nick.nickfolio.database.StockDatabase
import itmo.nick.nickfolio.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)

        if (isFirstRun) {
        val db = StockDatabase.getDatabase(application)
        val stockRepository = db.stockDao()

        val stocks =  listOf(

            Stock(
                uid = 1,
                name = "Сбербанк",
                symbol = "SBER",
                sector = "Банки",
                price2023 = "141,15",
                price2022 = "293,49",
                price2021 = "271,7",
                price2020 = "254,8",
                price2019 = "186,3",
                price2018 = "225,2",
                price2017 = "173,3",
                price2016 = "101,3",
                price2015 = "54,9",
                price2014 = "101,2",
                price2013 = "92,9",
                dividend2023 = "10,5",
                dividend2022 = "0",
                dividend2021 = "5,8",
                dividend2020 = "8,2",
                dividend2019 = "6,4",
                dividend2018 = "5,6",
                dividend2017 = "4",
                dividend2016 = "1,4",
                dividend2015 = "0,6",
                dividend2014 = "3,6",
                dividend2013 = "2,5",
                currency = "RUB"
            ), Stock(
                uid = 2,
                name = "Газпром",
                symbol = "GAZP",
                sector = "Нефтегаз",
                price2023 = "300",
                price2022 = "250",
                price2021 = "200",
                price2020 = "180",
                price2019 = "150",
                price2018 = "130",
                price2017 = "110",
                price2016 = "90",
                price2015 = "70",
                price2014 = "50",
                price2013 = "30",
                dividend2023 = "6",
                dividend2022 = "5,50",
                dividend2021 = "5",
                dividend2020 = "4,50",
                dividend2019 = "4",
                dividend2018 = "3,50",
                dividend2017 = "3",
                dividend2016 = "2,50",
                dividend2015 = "2",
                dividend2014 = "1,50",
                dividend2013 = "1",
                currency = "USD"
            ), Stock(
                uid = 3,
                name = "Microsoft Corporation",
                symbol = "MSFT",
                sector = "Technology",
                price2023 = "300",
                price2022 = "250",
                price2021 = "200",
                price2020 = "180",
                price2019 = "150",
                price2018 = "130",
                price2017 = "110",
                price2016 = "90",
                price2015 = "70",
                price2014 = "50",
                price2013 = "30",
                dividend2023 = "6",
                dividend2022 = "5,50",
                dividend2021 = "5",
                dividend2020 = "4,50",
                dividend2019 = "4",
                dividend2018 = "3,50",
                dividend2017 = "3",
                dividend2016 = "2,50",
                dividend2015 = "2",
                dividend2014 = "1,50",
                dividend2013 = "1",
                currency = "USD"
            ), Stock(
                uid = 4,
                name = "Microsoft Corporation",
                symbol = "MSFT",
                sector = "Technology",
                price2023 = "300",
                price2022 = "250",
                price2021 = "200",
                price2020 = "180",
                price2019 = "150",
                price2018 = "130",
                price2017 = "110",
                price2016 = "90",
                price2015 = "70",
                price2014 = "50",
                price2013 = "30",
                dividend2023 = "6",
                dividend2022 = "5,50",
                dividend2021 = "5",
                dividend2020 = "4,50",
                dividend2019 = "4",
                dividend2018 = "3,50",
                dividend2017 = "3",
                dividend2016 = "2,50",
                dividend2015 = "2",
                dividend2014 = "1,50",
                dividend2013 = "1",
                currency = "USD"
            ), Stock(
                uid = 5,
                name = "Microsoft Corporation",
                symbol = "MSFT",
                sector = "Technology",
                price2023 = "300",
                price2022 = "250",
                price2021 = "200",
                price2020 = "180",
                price2019 = "150",
                price2018 = "130",
                price2017 = "110",
                price2016 = "90",
                price2015 = "70",
                price2014 = "50",
                price2013 = "30",
                dividend2023 = "6",
                dividend2022 = "5,50",
                dividend2021 = "5",
                dividend2020 = "4,50",
                dividend2019 = "4",
                dividend2018 = "3,50",
                dividend2017 = "3",
                dividend2016 = "2,50",
                dividend2015 = "2",
                dividend2014 = "1,50",
                dividend2013 = "1",
                currency = "USD"
            ), Stock(
                uid = 6,
                name = "Microsoft Corporation",
                symbol = "MSFT",
                sector = "Technology",
                price2023 = "300",
                price2022 = "250",
                price2021 = "200",
                price2020 = "180",
                price2019 = "150",
                price2018 = "130",
                price2017 = "110",
                price2016 = "90",
                price2015 = "70",
                price2014 = "50",
                price2013 = "30",
                dividend2023 = "6",
                dividend2022 = "5,50",
                dividend2021 = "5",
                dividend2020 = "4,50",
                dividend2019 = "4",
                dividend2018 = "3,50",
                dividend2017 = "3",
                dividend2016 = "2,50",
                dividend2015 = "2",
                dividend2014 = "1,50",
                dividend2013 = "1",
                currency = "USD"
            ), Stock(
                uid = 7,
                name = "Microsoft Corporation",
                symbol = "MSFT",
                sector = "Technology",
                price2023 = "300",
                price2022 = "250",
                price2021 = "200",
                price2020 = "180",
                price2019 = "150",
                price2018 = "130",
                price2017 = "110",
                price2016 = "90",
                price2015 = "70",
                price2014 = "50",
                price2013 = "30",
                dividend2023 = "6",
                dividend2022 = "5,50",
                dividend2021 = "5",
                dividend2020 = "4,50",
                dividend2019 = "4",
                dividend2018 = "3,50",
                dividend2017 = "3",
                dividend2016 = "2,50",
                dividend2015 = "2",
                dividend2014 = "1,50",
                dividend2013 = "1",
                currency = "USD"
            ), Stock(
                uid = 8,
                name = "Microsoft Corporation",
                symbol = "MSFT",
                sector = "Technology",
                price2023 = "300",
                price2022 = "250",
                price2021 = "200",
                price2020 = "180",
                price2019 = "150",
                price2018 = "130",
                price2017 = "110",
                price2016 = "90",
                price2015 = "70",
                price2014 = "50",
                price2013 = "30",
                dividend2023 = "6",
                dividend2022 = "5,50",
                dividend2021 = "5",
                dividend2020 = "4,50",
                dividend2019 = "4",
                dividend2018 = "3,50",
                dividend2017 = "3",
                dividend2016 = "2,50",
                dividend2015 = "2",
                dividend2014 = "1,50",
                dividend2013 = "1",
                currency = "USD"
            ), Stock(
                uid = 9,
                name = "Microsoft Corporation",
                symbol = "MSFT",
                sector = "Technology",
                price2023 = "300",
                price2022 = "250",
                price2021 = "200",
                price2020 = "180",
                price2019 = "150",
                price2018 = "130",
                price2017 = "110",
                price2016 = "90",
                price2015 = "70",
                price2014 = "50",
                price2013 = "30",
                dividend2023 = "6",
                dividend2022 = "5,50",
                dividend2021 = "5",
                dividend2020 = "4,50",
                dividend2019 = "4",
                dividend2018 = "3,50",
                dividend2017 = "3",
                dividend2016 = "2,50",
                dividend2015 = "2",
                dividend2014 = "1,50",
                dividend2013 = "1",
                currency = "USD"
            ), Stock(
                uid = 10,
                name = "Microsoft Corporation",
                symbol = "MSFT",
                sector = "Technology",
                price2023 = "300",
                price2022 = "250",
                price2021 = "200",
                price2020 = "180",
                price2019 = "150",
                price2018 = "130",
                price2017 = "110",
                price2016 = "90",
                price2015 = "70",
                price2014 = "50",
                price2013 = "30",
                dividend2023 = "6",
                dividend2022 = "5,50",
                dividend2021 = "5",
                dividend2020 = "4,50",
                dividend2019 = "4",
                dividend2018 = "3,50",
                dividend2017 = "3",
                dividend2016 = "2,50",
                dividend2015 = "2",
                dividend2014 = "1,50",
                dividend2013 = "1",
                currency = "USD"
            ), Stock(
                uid = 11,
                name = "Microsoft Corporation",
                symbol = "MSFT",
                sector = "Technology",
                price2023 = "300",
                price2022 = "250",
                price2021 = "200",
                price2020 = "180",
                price2019 = "150",
                price2018 = "130",
                price2017 = "110",
                price2016 = "90",
                price2015 = "70",
                price2014 = "50",
                price2013 = "30",
                dividend2023 = "6",
                dividend2022 = "5,50",
                dividend2021 = "5",
                dividend2020 = "4,50",
                dividend2019 = "4",
                dividend2018 = "3,50",
                dividend2017 = "3",
                dividend2016 = "2,50",
                dividend2015 = "2",
                dividend2014 = "1,50",
                dividend2013 = "1",
                currency = "RUB"
            ), Stock(
                uid = 12,
                name = "Microsoft Corporation",
                symbol = "MSFT",
                sector = "Technology",
                price2023 = "300",
                price2022 = "250",
                price2021 = "200",
                price2020 = "180",
                price2019 = "150",
                price2018 = "130",
                price2017 = "110",
                price2016 = "90",
                price2015 = "70",
                price2014 = "50",
                price2013 = "30",
                dividend2023 = "6",
                dividend2022 = "5,50",
                dividend2021 = "5",
                dividend2020 = "4,50",
                dividend2019 = "4",
                dividend2018 = "3,50",
                dividend2017 = "3",
                dividend2016 = "2,50",
                dividend2015 = "2",
                dividend2014 = "1,50",
                dividend2013 = "1",
                currency = "RUB"
            ), Stock(
                uid = 13,
                name = "Microsoft Corporation",
                symbol = "MSFT",
                sector = "Technology",
                price2023 = "300",
                price2022 = "250",
                price2021 = "200",
                price2020 = "180",
                price2019 = "150",
                price2018 = "130",
                price2017 = "110",
                price2016 = "90",
                price2015 = "70",
                price2014 = "50",
                price2013 = "30",
                dividend2023 = "6",
                dividend2022 = "5,50",
                dividend2021 = "5",
                dividend2020 = "4,50",
                dividend2019 = "4",
                dividend2018 = "3,50",
                dividend2017 = "3",
                dividend2016 = "2,50",
                dividend2015 = "2",
                dividend2014 = "1,50",
                dividend2013 = "1",
                currency = "RUB"
            ), Stock(
                uid = 14,
                name = "Microsoft Corporation",
                symbol = "MSFT",
                sector = "Technology",
                price2023 = "300",
                price2022 = "250",
                price2021 = "200",
                price2020 = "180",
                price2019 = "150",
                price2018 = "130",
                price2017 = "110",
                price2016 = "90",
                price2015 = "70",
                price2014 = "50",
                price2013 = "30",
                dividend2023 = "6",
                dividend2022 = "5,50",
                dividend2021 = "5",
                dividend2020 = "4,50",
                dividend2019 = "4",
                dividend2018 = "3,50",
                dividend2017 = "3",
                dividend2016 = "2,50",
                dividend2015 = "2",
                dividend2014 = "1,50",
                dividend2013 = "1",
                currency = "USD"
            )
        )
        runBlocking {
            launch(Dispatchers.IO) {
                for(stock in stocks) {
                    stockRepository.insert(stock)
                }
            }
        }
            sharedPreferences.edit().putBoolean("isFirstRun", false).apply()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun transit() {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        navController.navigate(R.id.action_nav_home_to_stockDescriptionFragment)
    }
}