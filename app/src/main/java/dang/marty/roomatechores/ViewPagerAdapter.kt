package dang.marty.roomatechores

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import dang.marty.roomatechores.ui.fragments.LoginFrag
import dang.marty.roomatechores.ui.fragments.RegistrationFrag

/**
 *   Created by Marty Dang on 2020-01-16
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
class ViewPagerAdapter(fm: FragmentManager, int: Int) : FragmentStatePagerAdapter(fm, int) {
    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return LoginFrag()
            1 -> return RegistrationFrag()
        }
        return LoginFrag()
    }

}