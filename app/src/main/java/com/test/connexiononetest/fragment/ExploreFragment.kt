package com.test.connexiononetest.fragment

import android.os.Bundle
import android.view.View
import com.test.connexiononetest.R
import com.test.connexiononetest.base.BaseFragment

class ExploreFragment : BaseFragment() {

    companion object {
        fun newInstance(): ExploreFragment {
            return ExploreFragment()
        }
    }

    override fun initial(savedInstanceState: Bundle?, view: View) {
    }

    override fun onResource(): Int = R.layout.fragment_explore
}