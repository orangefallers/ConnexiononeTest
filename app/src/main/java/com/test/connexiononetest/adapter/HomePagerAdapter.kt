package com.test.connexiononetest.adapter

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.io.Serializable

class HomePagerAdapter(manager: FragmentManager)
    : FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var mCurrentPage: Fragment? = null
    private var _itemList: List<FragmentData>? = null
    var itemList: List<FragmentData>?
        get() = _itemList
        set(value) {
            _itemList = value
            notifyDataSetChanged()
        }

    class FragmentData(val title: String,
                       val fragmentClass: Class<out Fragment>,
                       var argument: Map<String, Serializable>? = null)

    override fun getCount(): Int {
        return itemList?.size ?: 0
    }

    override fun getItem(position: Int): Fragment {
        val data = itemList?.getOrElse(position) { null }
        val fragment = data?.fragmentClass?.newInstance() ?: Fragment()
        val argument = Bundle()
        for (key in data?.argument?.keys ?: emptySet()) {
            data?.argument?.getOrElse(key) { null }?.let {
                argument.putSerializable(key, it)
            }
        }

        fragment.arguments = argument
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val data = itemList?.getOrElse(position) { null }
        return data?.title ?: ""
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        if (getCurrentPage() != `object`) {
            mCurrentPage = `object` as Fragment
        }

        super.setPrimaryItem(container, position, `object`)
    }

    fun getCurrentPage(): Fragment? {
        return mCurrentPage
    }
}