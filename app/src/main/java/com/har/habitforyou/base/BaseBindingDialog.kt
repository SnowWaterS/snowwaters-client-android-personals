package com.har.habitforyou.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.har.habitforyou.R
import com.har.habitforyou.databinding.DialogBaseBindingBinding

abstract class BaseBindingDialog<VB: ViewDataBinding, VM: BaseBindingDialogViewModel>: DialogFragment() {

    private var _baseBinding: DialogBaseBindingBinding? = null
    private val baseBinding get() = _baseBinding ?: throw NullPointerException("BaseBinding is null")

    private var _binding: VB? = null
    val binding get() = _binding ?: throw NullPointerException("ViewBinding is null")

    private lateinit var viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _baseBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_base_binding, container, false)
        baseBinding.lifecycleOwner = this

        _binding = DataBindingUtil.inflate(layoutInflater, getContentLayoutId(), container, false)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(getViewModelClass())
        baseBinding.viewModel = viewModel
        initBinding(viewModel, binding)

        val contentView = binding.root
        val laytoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)

        baseBinding.layBaseContent.addView(contentView, laytoutParams)

        return baseBinding.root
    }

    protected abstract fun getContentLayoutId(): Int
    protected abstract fun getViewModelClass(): Class<VM>
    protected abstract fun initBinding(viewModel: VM, binding: VB)
}