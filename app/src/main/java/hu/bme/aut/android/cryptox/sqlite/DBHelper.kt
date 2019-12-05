package hu.bme.aut.android.cryptox.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import hu.bme.aut.android.cryptox.sqlite.table.CoinTable

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        CoinTable.onCreate(sqLiteDatabase)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        CoinTable.onUpgrade(sqLiteDatabase, oldVersion, newVersion)
    }

    companion object {
        private val DATABASE_NAME = "cryptoX.db"

        private val DATABASE_VERSION = 1
    }
}