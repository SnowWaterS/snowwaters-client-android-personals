package com.har.habitforyou.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.har.habitforyou.R
import com.har.habitforyou.onboarding.OnBoardingFragment
import java.util.*

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

        Log.i("list test", "home fragment")
        test()
    }

    fun test() {
        Log.i("list test", "home test")

        val multiList = mutableListOf<Any>()
        for (i in 0 until 10) {
            val curTest1 = Test1(i.toLong(), "name $i", i)
            multiList.add(curTest1)
        }

        for (i in 4 until 13 step 2) {
            val curTest2 = Test2(i.toLong(), "testName = test$i", Calendar.getInstance().time)
            multiList.add(curTest2)
        }

        val newList = multiList.sortedBy {
            when (it) {
                is Test1 -> {
                    it.id
                }
                is Test2 -> {
                    it.id
                }
                else -> {
                    -1
                }
            }
        }

        Log.i("list test", "newList = ${newList}")
    }

}

data class Test1(
        val id: Long = 0L,
        val name: String = "",
        val value: Int = 0
)

data class Test2(
        val id: Long = 0L,
        val testName: String = "",
        val date: Date = Calendar.getInstance().time
)