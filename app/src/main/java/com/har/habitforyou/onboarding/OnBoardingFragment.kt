package com.har.habitforyou.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.har.habitforyou.R
import com.har.habitforyou.databinding.FragmentOnboardingBinding

class OnBoardingFragment: DialogFragment(), OnBoardingCloseListener {

    companion object {
        fun newInstance() = OnBoardingFragment()
    }

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding ?: throw NullPointerException("FragmentOnboardingBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BaseDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_onboarding, container, false)

        val onBoardingPagerAdapter = OnBoardingPagerAdapter(this)
        onBoardingPagerAdapter.setCloseListener(this)
        binding.vpOnboarding.adapter = onBoardingPagerAdapter
        binding.vpOnboarding.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.tlOnboarding, binding.vpOnboarding) { _, _ -> }.attach()
        return binding.root
    }

    override fun onDismss() {
        dismissAllowingStateLoss()
    }
}