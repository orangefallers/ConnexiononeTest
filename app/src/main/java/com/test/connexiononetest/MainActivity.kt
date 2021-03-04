package com.test.connexiononetest

import android.os.Bundle
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.test.connexiononetest.base.BaseActivity
import com.test.connexiononetest.fragment.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun initial(savedInstanceState: Bundle?) {
        // Init tab bar
        initTabBar()

        rbWallet?.isChecked = true

    }

    override fun onResource(): Int = R.layout.activity_main

    override fun showBaseLoadingDialog() {
    }

    override fun hideBaseLoadingDialog() {
    }

    private fun initTabBar() {
        rgTabBar?.setOnCheckedChangeListener { _, checkedId ->
            if (findViewById<RadioButton>(checkedId)?.isChecked == true) {
                when (checkedId) {
                    R.id.rbPhone -> {
                        showFragmentPrimary(PhoneFragment.newInstance())
                    }
                    R.id.rbChat -> {
                        showFragmentPrimary(ChatFragment.newInstance())
                    }
                    R.id.rbUnknown -> {
                        showFragmentPrimary(UnknownFragment.newInstance())
                    }
                    R.id.rbExplore -> {
                        showFragmentPrimary(ExploreFragment.newInstance())
                    }
                    R.id.rbWallet -> {
                        showFragmentPrimary(WalletFragment.newInstance())
                    }
                }
            }
        }
    }

    private fun showFragmentPrimary(fragment: Fragment, isFocusCommit: Boolean = false) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentRoot)
        if (fragment::class.java.isInstance(currentFragment) && isFocusCommit.not()) {
            return
        }
        val transaction = supportFragmentManager.beginTransaction()
        val isForward = getFragmentOrdering(fragment) >= getFragmentOrdering(currentFragment)
        if (isForward) {
            transaction.setCustomAnimations(
                R.anim.anim_start_enter,
                R.anim.anim_start_out,
                R.anim.anim_end_enter,
                R.anim.anim_end_out
            )
        } else {
            transaction.setCustomAnimations(
                R.anim.anim_end_enter,
                R.anim.anim_end_out,
                R.anim.anim_start_enter,
                R.anim.anim_start_out
            )
        }
        transaction.replace(R.id.fragmentRoot, fragment).commit()
    }

    private fun getFragmentOrdering(fragment: Fragment?): Int {
        return when (fragment) {
            is PhoneFragment -> 1
            is ChatFragment -> 2
            is UnknownFragment -> 3
            is ExploreFragment -> 4
            is WalletFragment -> 5
            else -> Int.MAX_VALUE
        }
    }

}