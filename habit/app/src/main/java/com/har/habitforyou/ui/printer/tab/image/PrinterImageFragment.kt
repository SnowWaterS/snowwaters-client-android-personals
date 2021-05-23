package com.har.habitforyou.ui.printer.tab.image

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.har.habitforyou.R
import com.har.habitforyou.databinding.FragmentPrinterImageBinding
import com.har.habitforyou.util.PrinterUtil

class PrinterImageFragment : Fragment() {

    companion object {
        fun newInstance() = PrinterImageFragment()
    }

    private var _binding: FragmentPrinterImageBinding? = null
    private val binding
        get() = _binding ?: throw NullPointerException("FragmentPrinterImageBinding is Null")
    private val viewModel: PrinterImageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_printer_image, container, false)
        return binding.root
    }

    private val takenPhoto =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            Log.i("takenPhoto", "Show Preivew")
            binding.ivLoadImage.setImageBitmap(it)
        }

    private val loadedPhoto = registerForActivityResult(ActivityResultContracts.GetContent()) {
        Log.i("loadPhoto", "uri? $it")
        binding.ivLoadImage.setImageURI(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        binding.btnTakePhoto.setOnClickListener {
            takenPhoto.launch()
        }

        binding.btnLoadPhoto.setOnClickListener {
            loadedPhoto.launch("image/*")
        }

        binding.btnRemovePhoto.setOnClickListener {
            binding.ivLoadImage.setImageDrawable(null)
        }

        binding.btnPrintImage.setOnClickListener {
            val image = binding.ivLoadImage.drawable?.toBitmap() ?: run {
                Toast.makeText(requireContext(), "선택된 이미지가 없습니다.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            Log.i("PrinterImage", "drawable is not null")
            PrinterUtil.printImage(image)
        }
    }

}