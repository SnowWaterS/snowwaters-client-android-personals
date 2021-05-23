package com.har.habitforyou.ui.printer.tab.extension

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.har.habitforyou.R
import com.har.habitforyou.databinding.FragmentPrinterExtensionBinding
import com.har.habitforyou.databinding.FragmentPrinterImageBinding

class PrinterExtensionFragment : Fragment() {

    companion object {
        fun newInstance() = PrinterExtensionFragment()
    }

    private var _binding: FragmentPrinterExtensionBinding? = null
    private val binding get() = _binding ?: throw NullPointerException("FragmentPrinterExtensionBinding is Null")

    private val viewModel: PrinterExtensionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_printer_extension, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
    }

}