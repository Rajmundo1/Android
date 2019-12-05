package hu.bme.aut.android.cryptox.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    companion object{
        val listFragment = mutableListOf<Fragment>()
        val listTitles = mutableListOf<String>()
    }

    override fun getItem(position: Int): Fragment {
        return listFragment[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return listTitles[position]
    }

    override fun getCount(): Int {
        return listFragment.size
    }

    fun AddFragment (fragment: Fragment, title: String){
        listFragment.add(fragment)
        listTitles.add(title)
    }

}