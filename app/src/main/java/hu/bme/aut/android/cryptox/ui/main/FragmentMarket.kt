package hu.bme.aut.android.cryptox.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.cryptox.MainActivity
import hu.bme.aut.android.cryptox.R
import hu.bme.aut.android.cryptox.adapter.MarketAdapter

class FragmentMarket(mContext: MainActivity): Fragment() {

    lateinit var v:View
    lateinit var recyclerView: RecyclerView
    var marketAdapter = MarketAdapter(mContext)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.market_fragment, container, false)

        recyclerView = v.findViewById(R.id.market_rec_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = marketAdapter

        return v
    }

}