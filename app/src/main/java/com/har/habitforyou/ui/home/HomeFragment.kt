package com.har.habitforyou.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.har.habitforyou.R
import com.har.habitforyou.onboarding.OnBoardingFragment

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var fragmentViewModel: HomeFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val isFirst = sharedPref.getBoolean(context?.getString(R.string.pref_key_fist) ?: "", false)
        if (!isFirst) {
            sharedPref.edit().putBoolean(context?.getString(R.string.pref_key_fist) ?: "", true).apply()
            OnBoardingFragment.newInstance().show(parentFragmentManager, "onBoardingFragment")
        }
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentViewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
    }

}