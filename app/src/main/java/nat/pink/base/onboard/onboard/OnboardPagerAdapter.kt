package com.example.dictionary.ui.onboard.onboard

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class OnboardPagerAdapter(
    var listPage : MutableList<Fragment>,
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int = listPage.size

    @SuppressLint("ResourceType")
    override fun getItem(position: Int): Fragment = listPage[position]
}
