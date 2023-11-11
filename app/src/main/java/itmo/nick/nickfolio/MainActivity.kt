package itmo.nick.nickfolio

import android.content.Context
import android.os.Bundle
import android.view.Menu
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import itmo.nick.nickfolio.database.DataInit
import itmo.nick.nickfolio.database.Stock
import itmo.nick.nickfolio.database.StockDatabase
import itmo.nick.nickfolio.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)

        if (isFirstRun) {
           DataInit.StockDataInit(sharedPreferences, application)
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

    fun showStockDescriptionFragment(stockName: String) {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val bundle = Bundle()

        // Запускаем корутину для асинхронного получения данных из базы данных
        CoroutineScope(Dispatchers.IO).launch {
            getDataFromDatabaseToStockDescriptionBundle(stockName, bundle)
            withContext(Dispatchers.Main) {
                bundle.putString("stockName", stockName)
                navController.navigate(R.id.action_nav_home_to_stockDescriptionFragment, bundle)
            }
        }
    }

    private suspend fun getDataFromDatabaseToStockDescriptionBundle(
        stockName: String,
        bundle: Bundle
    ) {
        val db = StockDatabase.getDatabaseStock(application)
        val stockRepository = db.stockDao()

        // Асинхронно получаем данные из базы данных
        withContext(Dispatchers.IO) {
            bundle.putString("stockSymbol", stockRepository.getSymbolByName(stockName))
            bundle.putString("stockSector", stockRepository.getSectorByName(stockName))
            bundle.putString("stockPrice2023", stockRepository.getPrice2023ByName(stockName))
            bundle.putString("stockDividend2023", stockRepository.getDividend2023ByName(stockName))
            bundle.putString("stockCurrency", stockRepository.getCurrencyByName(stockName))
        }
    }

    fun showOfferDescriptionFragment(offerName: String) {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val bundle = Bundle()
        bundle.putString("offerName", offerName)
        navController.navigate(R.id.action_nav_gallery_to_offerDescriptionFragment, bundle)
    }
}