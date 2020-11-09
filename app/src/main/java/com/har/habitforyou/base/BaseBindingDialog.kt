package com.har.habitforyou.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.har.habitforyou.R
import com.har.habitforyou.databinding.DialogBaseBindingBinding

abstract class BaseBindingDialog<VB : ViewDataBinding, VM : BaseBindingDialogViewModel> :
    DialogFragment() {

    private var _baseBinding: DialogBaseBindingBinding? = null
    private val baseBinding
        get() = _baseBinding ?: throw NullPointerException("BaseBinding is null")

    private var _binding: VB? = null
    val binding
        get() = _binding ?: throw NullPointerException("ViewBinding is null")

    private lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BaseDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _baseBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_base_binding, container, false)
        baseBinding.lifecycleOwner = this

        _binding = DataBindingUtil.inflate(layoutInflater, getContentLayoutId(), container, false)
        binding.lifecycleOwner = this

        val contentView = binding.root
        val laytoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        baseBinding.layBaseContent.addView(contentView, laytoutParams)

        return baseBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(getViewModelClass())
        viewModel.getDismissEvent().observe(this, Observer { isDismiss ->
            if (isDismiss) {
                dismissAllowingStateLoss()
            }
        })
        baseBinding.viewModel = viewModel
        initBinding(viewModel, binding)
    }

    override fun onDestroy() {
        super.onDestroy()

        baseBinding.layBaseContent.removeAllViews()
        _baseBinding = null
        _binding = null
    }


    protected abstract fun getContentLayoutId(): Int
    protected abstract fun getViewModelClass(): Class<VM>
    protected abstract fun initBinding(viewModel: VM, binding: VB)
}