package com.har.habitforyou.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.har.habitforyou.R
import com.har.habitforyou.databinding.FragmentOnboardingPagerBinding

class OnBoardingPagerFragment: Fragment() {

    companion object {

        private const val ARG_POSITION_KEY = "position"

        fun newInstance(position: Int): OnBoardingPagerFragment {
            val fragment = OnBoardingPagerFragment()
            val args = Bundle()
            args.putInt(ARG_POSITION_KEY, position)
            fragment.arguments = args
            return fragment
        }
    }

    private var position = -1;

    private var _binding: FragmentOnboardingPagerBinding? = null
    private val binding get() = _binding ?: throw NullPointerException("FragmentOnboardPagerBinding is null")
    lateinit var viewModel: OnBoardingPagerFragmentViewModel

    private var onBoardingCloseListener: OnBoardingCloseListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_onboarding_pager, container, false)

        val bundle = arguments
        if (bundle != null) {
            this.position = bundle.getInt(ARG_POSITION_KEY, -1)
        }

        viewModel = ViewModelProvider(this).get(OnBoardingPagerFragmentViewModel::class.java)
        viewModel.setPosition(position)

        viewModel.dismissEvent.observe(viewLifecycleOwner, { isDismiss ->
            if (isDismiss) {
                onBoardingCloseListener?.onDismss()
            }
        })

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    fun setCloseListener(onBoardingCloseListener: OnBoardingCloseListener) {
        this.onBoardingCloseListener = onBoardingCloseListener
    }

}