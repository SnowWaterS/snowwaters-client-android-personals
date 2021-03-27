package com.har.habitforyou.ui.printer.tab.image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.har.habitforyou.R
import com.har.habitforyou.databinding.FragmentPrinterImageBinding

class PrinterImageFragment : Fragment() {

    companion object {
        fun newInstance() = PrinterImageFragment()
    }

    private var _binding: FragmentPrinterImageBinding? = null
    private val binding get() = _binding ?: throw NullPointerException("FragmentPrinterImageBinding is Null")
    private val viewModel: PrinterImageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_printer_image, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
    }

}