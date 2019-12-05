package hu.bme.aut.android.cryptox.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hu.bme.aut.android.cryptox.MainActivity
import hu.bme.aut.android.cryptox.R
import hu.bme.aut.android.cryptox.model.Coin
import kotlinx.android.synthetic.main.coin_row.view.*
import okhttp3.OkHttpClient
import java.lang.StringBuilder


class MarketAdapter(internal var mContext: MainActivity) : RecyclerView.Adapter<MarketAdapter.ViewHolder>() {

    var marklist = ArrayList<Coin>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.coin_row, parent, false)
        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coin = marklist[position]

        holder.tvNumber.text = (position+1).toString()
        holder.tvName.text = coin.name
        holder.tvPrice.text = coin.price_usd.toString() + "$"

        holder.item = coin



        if(coin.favorite == 1) holder.favStar.setImageResource(holder.whiteFullStar)
        if(coin.favorite == 0) holder.favStar.setImageResource(holder.whiteEmptyStar)


        var url = StringBuilder(holder.imageUrl).append(coin.symbol).append(".png").toString()


            Picasso.with(mContext.baseContext)
                .load(url)
                .placeholder(holder.ivGenericID)
                .error(holder.ivGenericID)
                .into(holder.ivIcon)

    }

    override fun getItemCount(): Int {
        return marklist.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvNumber: TextView
        var tvName: TextView
        var tvPrice: TextView
        var ivIcon: ImageView
        var ivGenericID:Int
        var imageUrl:String
        var favStar: ImageView

        var whiteFullStar:Int
        var whiteEmptyStar:Int

        var context:Context
        var client: OkHttpClient

        lateinit var item: Coin

        init {
            tvNumber = itemView.coin_row_date
            tvName = itemView.coin_row_name
            tvPrice = itemView.coin_row_price
            ivIcon = itemView.coin_row_icon
            ivGenericID = R.drawable.generic
            imageUrl = "https://res.cloudinary.com/dxi90ksom/image/upload/"
            favStar = itemView.coin_iv_favorite

            whiteFullStar = R.drawable.ic_star_white
            whiteEmptyStar = R.drawable.ic_star_empty

            context = itemView.context
            client = OkHttpClient()

            favStar.setOnClickListener {
                var oldCoin = item;
                var newCoin = Coin(oldCoin)


                if(newCoin.favorite == 0) newCoin.favorite = 1
                else if(newCoin.favorite == 1) newCoin.favorite = 0

                mContext.coinChanged(oldCoin,newCoin)
            }
        }
    }

    fun addItem(coin: Coin) {
        val size = marklist.size
        marklist.add(coin)
        notifyItemInserted(size)
    }

    fun addList(coins: MutableList<Coin>) {
        val size = marklist.size
        marklist.plusAssign(coins)
        notifyItemRangeInserted(size, coins.size)
    }

    fun deleteAll() {
        val size = marklist.size
        marklist.clear()
        notifyItemRangeRemoved(0, size)
    }


}