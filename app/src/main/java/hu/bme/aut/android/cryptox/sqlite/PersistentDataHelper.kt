package hu.bme.aut.android.cryptox.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.os.AsyncTask
import hu.bme.aut.android.cryptox.model.Coin
import hu.bme.aut.android.cryptox.sqlite.table.CoinTable
import java.util.ArrayList

class PersistentDataHelper(context: Context): AsyncTask<Unit,Unit, MutableList<Coin>>() {

    private val coins = ArrayList<Coin>()

    private var database: SQLiteDatabase? = null

    private val dbHelper: DBHelper


    private val coinColumns = arrayOf<String>(
        CoinTable.Columns._id.name,
        CoinTable.Columns.coinID.name,
        CoinTable.Columns.coinName.name,
        CoinTable.Columns.symbol.name,
        CoinTable.Columns.rank.name,
        CoinTable.Columns.price_usd.name,
        CoinTable.Columns.price_btc.name,
        CoinTable.Columns.volume_usd_24h.name,
        CoinTable.Columns.market_cap_usd.name,
        CoinTable.Columns.available_supply.name,
        CoinTable.Columns.total_supply.name,
        CoinTable.Columns.max_supply.name,
        CoinTable.Columns.percent_change_1h.name,
        CoinTable.Columns.percent_change_24h.name,
        CoinTable.Columns.percent_change_7d.name,
        CoinTable.Columns.last_updated.name,
        CoinTable.Columns.favorite.name
    )


    init {
        dbHelper = DBHelper(context)
    }

    @Throws(SQLiteException::class)
    fun open() {
        database = dbHelper.writableDatabase
    }

    fun getCoins(): List<Coin> {
        return coins
    }

    fun close() {
        dbHelper.close()
    }

    fun persistCoin(coins: MutableList<Coin>) {
        clearCoins()
        for (item in coins) {
            val values = ContentValues()
            values.put(CoinTable.Columns.coinID.name, item.id)
            values.put(CoinTable.Columns.coinName.name, item.name)
            values.put(CoinTable.Columns.symbol.name, item.symbol)
            values.put(CoinTable.Columns.rank.name, item.rank)
            values.put(CoinTable.Columns.price_usd.name, item.price_usd)
            values.put(CoinTable.Columns.price_btc.name, item.price_btc)
            values.put(CoinTable.Columns.volume_usd_24h.name, item.volume_usd_24h)
            values.put(CoinTable.Columns.market_cap_usd.name, item.market_cap_usd)
            values.put(CoinTable.Columns.available_supply.name, item.available_supply)
            values.put(CoinTable.Columns.total_supply.name, item.total_supply)
            values.put(CoinTable.Columns.max_supply.name, item.max_supply)
            values.put(CoinTable.Columns.percent_change_1h.name, item.percent_change_1h)
            values.put(CoinTable.Columns.percent_change_24h.name, item.percent_change_24h)
            values.put(CoinTable.Columns.percent_change_7d.name, item.percent_change_7d)
            values.put(CoinTable.Columns.last_updated.name, item.last_updated)
            values.put(CoinTable.Columns.favorite.name, item.favorite)
            database!!.insert(CoinTable.TABLE_COIN, null, values)
        }
    }

    override fun doInBackground(vararg params: Unit): MutableList<Coin> {
        val coins = ArrayList<Coin>()
        val cursor =
            database!!.query(CoinTable.TABLE_COIN, coinColumns, null, null, null, null, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val content = cursorToContent(cursor)
            coins.add(content)
            cursor.moveToNext()
        }
        cursor.close()
        return coins
    }

    fun restoreCoin(): MutableList<Coin>{
        return doInBackground()
    }

    fun dropAll() {
        database!!.execSQL("DROP TABLE IF EXISTS coin")
    }

    fun clearCoins() {
        database!!.delete(CoinTable.TABLE_COIN, null, null)
    }

    private fun cursorToContent(cursor: Cursor): Coin {
        val coin = Coin()

        coin.id = cursor.getString(CoinTable.Columns.coinID.ordinal)
        coin.name = cursor.getString(CoinTable.Columns.coinName.ordinal)
        coin.symbol = cursor.getString(CoinTable.Columns.symbol.ordinal)
        coin.rank = cursor.getString(CoinTable.Columns.rank.ordinal).toInt()
        coin.price_usd = cursor.getString(CoinTable.Columns.price_usd.ordinal).toDouble()
        coin.price_btc = cursor.getString(CoinTable.Columns.price_btc.ordinal).toDouble()
        coin.volume_usd_24h = cursor.getString(CoinTable.Columns.volume_usd_24h.ordinal).toDouble()
        coin.market_cap_usd = cursor.getString(CoinTable.Columns.market_cap_usd.ordinal).toDouble()
        coin.available_supply = cursor.getString(CoinTable.Columns.available_supply.ordinal).toDouble()
        coin.total_supply = cursor.getString(CoinTable.Columns.total_supply.ordinal).toDouble()
        coin.max_supply = cursor.getString(CoinTable.Columns.max_supply.ordinal).toDouble()
        coin.percent_change_1h = cursor.getString(CoinTable.Columns.percent_change_1h.ordinal).toDouble()
        coin.percent_change_24h = cursor.getString(CoinTable.Columns.percent_change_24h.ordinal).toDouble()
        coin.percent_change_7d = cursor.getString(CoinTable.Columns.percent_change_7d.ordinal).toDouble()
        coin.last_updated = cursor.getString(CoinTable.Columns.last_updated.ordinal).toDouble()
        coin.favorite = cursor.getString(CoinTable.Columns.favorite.ordinal).toInt()

        return coin
    }
}