package hu.bme.aut.android.cryptox

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.annotation.RequiresApi
import hu.bme.aut.android.cryptox.network.DataAndNetwork.Companion.formattedList
import hu.bme.aut.android.cryptox.model.Coin
import hu.bme.aut.android.cryptox.network.DataAndNetwork
import hu.bme.aut.android.cryptox.sqlite.PersistentDataHelper
import hu.bme.aut.android.cryptox.ui.main.FragmentFavorite
import hu.bme.aut.android.cryptox.ui.main.FragmentMarket
import hu.bme.aut.android.cryptox.ui.main.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import android.app.NotificationManager



class MainActivity : AppCompatActivity() {

    lateinit var  marketFragment: FragmentMarket
    lateinit var favoriteFragment: FragmentFavorite

    lateinit var database:PersistentDataHelper
    var startFlag: Boolean = true
    private val myNotificationId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        marketFragment = FragmentMarket(this)
        favoriteFragment = FragmentFavorite(this)

        val viewPager: ViewPager = findViewById(R.id.view_pager)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)

        sectionsPagerAdapter.AddFragment(marketFragment, "Market")
        sectionsPagerAdapter.AddFragment(favoriteFragment, "Favorites")

        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        tabs.getTabAt(0)?.setIcon(R.drawable.ic_star_white)
        tabs.getTabAt(1)?.setIcon(R.drawable.ic_money_white)

        btn_refresh.setOnClickListener{
            database.persistCoin(formattedList)
            DataAndNetwork(this).execute()
            Thread.sleep(3000)
            if(formattedList.size == 0){
                formattedList = database.restoreCoin()
                Toast.makeText(baseContext, "No internet connection, coins are fetched from the database", Toast.LENGTH_LONG).show()
            }
            else{
                checkFavs()
            }
            initLists()
        }
        DataAndNetwork(this).execute()
        database = PersistentDataHelper(this)
        database.open()
    }

    private fun checkFavs() {
        var dbList = database.restoreCoin()
        for(i in 0 until dbList.size){
            if(dbList[i].favorite == 1)
                formattedList[i].favorite = 1
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()

        Thread.sleep(4000)

        if(startFlag){
            if(formattedList.size == 0){
                formattedList = database.restoreCoin()
                Toast.makeText(baseContext, "No internet connection, coins are fetched from the database", Toast.LENGTH_LONG).show()
            }
            else{
                checkFavs()
            }
            initLists()
            startFlag = false
        }
        database.open()

        displayNotification(createBasicNotification())
    }

    override fun onPause() {
        super.onPause()
        database.persistCoin(formattedList)
        database.close()
    }

    fun initLists(){
        marketFragment.marketAdapter.deleteAll()
        favoriteFragment.favoriteAdapter.deleteAll()

        marketFragment.marketAdapter.addList(formattedList)

        for(i in 0 until formattedList.size){
            if(formattedList[i].favorite == 1)
                favoriteFragment.favoriteAdapter.addItem(formattedList[i])
        }
    }

    fun coinChanged(old: Coin, new: Coin){
        var list = formattedList
        var dummylist = ArrayList<Coin>()

        for(item in list){
            if(item.equals(old))
                dummylist.add(new)
            else
                dummylist.add(item)
        }
        formattedList = dummylist

        restorePersistedContent()
    }

    private fun restorePersistedContent() {
        var list = formattedList

        marketFragment.marketAdapter.deleteAll()
        favoriteFragment.favoriteAdapter.deleteAll()

        marketFragment.marketAdapter.addList(list)

        for(item in list){
            if(item.favorite == 1)
                favoriteFragment.favoriteAdapter.addItem(item)
        }
    }




    private fun createBasicNotification(): Notification {
        val builder = Notification.Builder(applicationContext)

        return builder
            .setSmallIcon(R.drawable.ic_noti)
            .setContentTitle("Good to see you back :)")
            .setContentText("BTC price:\t " + formattedList[0].price_usd + "$")
            .setAutoCancel(true)
            .build()
    }

    private fun displayNotification(notification: Notification) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(myNotificationId, notification)
    }
}