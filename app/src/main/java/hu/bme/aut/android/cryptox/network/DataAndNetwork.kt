package hu.bme.aut.android.cryptox.network

import android.app.ProgressDialog
import android.os.AsyncTask
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hu.bme.aut.android.cryptox.MainActivity
import hu.bme.aut.android.cryptox.model.Coin
import okhttp3.*
import java.io.IOException

class DataAndNetwork (act: MainActivity): AsyncTask<Unit, Unit, Unit>() {

    companion object{
        internal var list: MutableList<Coin> = ArrayList()
        internal var formattedList: MutableList<Coin> = ArrayList()
    }

    internal var client: OkHttpClient
    internal  lateinit var  request: Request
    var url: String

    var progressDialog: ProgressDialog

    init {
        client = OkHttpClient()
        url = "https://api.coinmarketcap.com/v1/ticker/?start=%d&limit=100"

        progressDialog = ProgressDialog(act)
    }


    override fun onPreExecute() {
        super.onPreExecute()
        progressDialog.setMessage("Fetching coins, please wait :)")
        progressDialog.show()
    }

    override fun doInBackground(vararg params: Unit?) {

        list.clear()
        formattedList.clear()
            request = Request.Builder().url(String.format(url, 0)).build()
            client.newCall(request).enqueue(object:Callback{
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("ERROR", e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body()!!.string()
                    val gson = Gson()
                    val newItems = gson.fromJson<List<Coin>>(body,object:TypeToken<List<Coin>>(){}.type)

                    list.addAll(newItems)

                    makeList()
                }
            })
    }

    override fun onPostExecute(result: Unit?) {
        super.onPostExecute(result)
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private fun makeList() {
        for (i in 0..list.size - 1) {
            var c = Coin(
                list[i].id,
                list[i].name,
                list[i].symbol,
                list[i].rank,
                list[i].price_usd,
                list[i].price_btc,
                list[i].volume_usd_24h,
                list[i].market_cap_usd,
                list[i].available_supply,
                list[i].total_supply,
                list[i].max_supply,
                list[i].percent_change_1h,
                list[i].percent_change_24h,
                list[i].percent_change_7d,
                list[i].last_updated
            )
            formattedList.add(c)
        }
    }
}