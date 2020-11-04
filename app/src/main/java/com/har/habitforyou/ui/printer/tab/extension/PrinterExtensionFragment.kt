package com.har.habitforyou.ui.printer.tab.extension

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.har.habitforyou.R

class PrinterExtensionFragment : Fragment() {

    companion object {
        fun newInstance() = PrinterExtensionFragment()
    }

    private lateinit var viewModel: PrinterExtensionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_printer_extension, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PrinterExtensionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}