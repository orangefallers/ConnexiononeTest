package com.test.connexiononetest.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import com.test.connexiononetest.R
import com.test.connexiononetest.adapter.HomePagerAdapter
import com.test.connexiononetest.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_wallet.*

class WalletFragment : BaseFragment() {

    companion object {
        fun newInstance(): WalletFragment {
            return WalletFragment()
        }
    }

    private val pagerAdapter: HomePagerAdapter by lazy {
        HomePagerAdapter(childFragmentManager)
    }

    override fun initial(savedInstanceState: Bundle?, view: View) {
        // Init view pager
        initViewPager()

        // Init tab layout
        initTabLayout()

        // Init data
        initData()
    }

    override fun onResource(): Int = R.layout.fragment_wallet

    private fun initViewPager() {
        // Fragment view pager
        viewPagerSlide?.adapter = pagerAdapter
    }

    private fun initTabLayout() {
        // Tab layout
        tabLayout?.setupWithViewPager(viewPagerSlide, true)
        tabLayout?.addOnTabSelectedListener(onTabSelectedListener)
    }

    private val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            val views = arrayListOf<View>()
            tab?.view?.findViewsWithText(views, tab.text, View.FIND_VIEWS_WITH_TEXT)
            views.forEach { view ->
                if (view is TextView) {
                    view.setTypeface(null, Typeface.BOLD)
                }
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            val views = arrayListOf<View>()
            tab?.view?.findViewsWithText(views, tab.text, View.FIND_VIEWS_WITH_TEXT)
            views.forEach { view ->
                if (view is TextView) {
                    view.setTypeface(null, Typeface.NORMAL)
                }
            }
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {

        }
    }


    private fun initData() {
        // Add fragment by list data
        val fragmentList = ArrayList<HomePagerAdapter.FragmentData>()

        // Record fragment
        fragmentList.add(
            HomePagerAdapter.FragmentData(
                title = "容幣紀錄",
                fragmentClass = RecordFragment::class.java
            )
        )

        // Coupon fragment
        fragmentList.add(
            HomePagerAdapter.FragmentData(
                title = "兌換券",
                fragmentClass = CouponFragment::class.java

            )
        )

        pagerAdapter.itemList = fragmentList

    }

}