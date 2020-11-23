package com.har.habitforyou.onboarding

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder

class OnBoardingPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment), OnBoardingCloseListener {

    private var onBoardingCloseListener: OnBoardingCloseListener? = null

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = OnBoardingPagerFragment.newInstance(position)
        fragment.setCloseListener(this)
        return fragment
    }

    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }

    fun setCloseListener(onBoardingCloseListener: OnBoardingCloseListener) {
        this.onBoardingCloseListener = onBoardingCloseListener
    }

    override fun onDismss() {
        onBoardingCloseListener?.onDismss()
    }

}