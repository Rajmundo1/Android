package hu.bme.aut.android.cryptox.sqlite.table

import android.database.sqlite.SQLiteDatabase
import android.util.Log

object CoinTable {
    val TABLE_COIN = "coin"

    private val DATABASE_CREATE = ("create table " + TABLE_COIN + "("
            + Columns._id.name + " integer primary key autoincrement, "
            + Columns.coinID.name + " text not null, "
            + Columns.coinName.name + " text not null, "
            + Columns.symbol.name + " text not null,"
            + Columns.rank.name + " integer, "
            + Columns.price_usd.name + " real not null, "
            + Columns.price_btc.name + " real not null,"
            + Columns.volume_usd_24h.name + " real not null, "
            + Columns.market_cap_usd.name + " real not null,"
            + Columns.available_supply.name + " real not null, "
            + Columns.total_supply.name + " real not null, "
            + Columns.max_supply.name + " real not null,"
            + Columns.percent_change_1h.name + " real not null,"
            + Columns.percent_change_24h.name + " real not null, "
            + Columns.percent_change_7d.name + " real not null, "
            + Columns.last_updated.name + " real not null,"
            + Columns.favorite.name + " integer not null "
            + ");")

    fun onCreate(database: SQLiteDatabase) {
        database.execSQL(DATABASE_CREATE)
    }

    fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.w(CoinTable::class.java.name, "Upgrading from version $oldVersion to $newVersion")
        database.execSQL("DROP TABLE IF EXISTS $TABLE_COIN")
        onCreate(database)
    }

    enum class Columns {
        _id,
        coinID,
        coinName,
        symbol,
        rank,
        price_usd,
        price_btc,
        volume_usd_24h,
        market_cap_usd,
        available_supply,
        total_supply,
        max_supply,
        percent_change_1h,
        percent_change_24h,
        percent_change_7d,
        last_updated,
        favorite
    }
}