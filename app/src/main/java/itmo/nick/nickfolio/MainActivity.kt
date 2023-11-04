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

        val db = StockDatabase.getDatabase(application)
        val stockRepository = db.stockDao()



        val stocks =  listOf(Stock(
            uid = 1,
            name = "Amazon.com Inc.",
            symbol = "AMZN",
            sector = "Retail",
            price2023 = "3500.00",
            price2022 = "3000.00",
            price2021 = "2500.00",
            price2020 = "2000.00",
            price2019 = "1700.00",
            price2018 = "1500.00",
            price2017 = "1200.00",
            price2016 = "1000.00",
            price2015 = "800.00",
            price2014 = "600.00",
            price2013 = "400.00",
            dividend2023 = "10.00",
            dividend2022 = "9.00",
            dividend2021 = "8.50",
            dividend2020 = "8.00",
            dividend2019 = "7.50",
            dividend2018 = "7.00",
            dividend2017 = "6.50",
            dividend2016 = "6.00",
            dividend2015 = "5.50",
            dividend2014 = "5.00",
            dividend2013 = "4.50",
            currency = "USD"
        ), Stock(
            uid = 2,
            name = "Microsoft Corporation",
            symbol = "MSFT",
            sector = "Technology",
            price2023 = "300.00",
            price2022 = "250.00",
            price2021 = "200.00",
            price2020 = "180.00",
            price2019 = "150.00",
            price2018 = "130.00",
            price2017 = "110.00",
            price2016 = "90.00",
            price2015 = "70.00",
            price2014 = "50.00",
            price2013 = "30.00",
            dividend2023 = "6.00",
            dividend2022 = "5.50",
            dividend2021 = "5.00",
            dividend2020 = "4.50",
            dividend2019 = "4.00",
            dividend2018 = "3.50",
            dividend2017 = "3.00",
            dividend2016 = "2.50",
            dividend2015 = "2.00",
            dividend2014 = "1.50",
            dividend2013 = "1.00",
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


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
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
}