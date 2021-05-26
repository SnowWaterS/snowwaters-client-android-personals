package com.har.onecommitaday.ui.main

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.PixelCopy
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.har.onecommitaday.R
import com.har.onecommitaday.databinding.CommitHistoryFragmentBinding
import com.har.onecommitaday.extensions.setVisibleIf
import com.har.onecommitaday.manager.SettingManager
import com.har.onecommitaday.widgets.AlphaAnimationListener
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.system.exitProcess


class CommitHistoryFragment : Fragment() {

    private val viewModel: CommitHistoryViewModel by viewModels()

    private var _binding: CommitHistoryFragmentBinding? = null
    private val binding
        get() = _binding ?: throw NullPointerException("CommitHistoryFragmentBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.commit_history_fragment,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

//        binding.tvCommitSummary.setBackgroundColor(resources.getColor(R.color.design_default_color_on_primary, null))
//        binding.btnCapture.setBackgroundColor(resources.getColor(R.color.design_default_color_on_primary, null))
//        binding.btnShare.setBackgroundColor(resources.getColor(R.color.design_default_color_on_primary, null))

        val typeAppearacne = SettingManager.getApperanceType(context)
        if (typeAppearacne == "jandi") {
            val jandiCommitHistoryAdapter = JandiCommitHistoryAdapter()
            binding.rvCommitHistory.adapter = jandiCommitHistoryAdapter
            binding.rvCommitHistory.layoutManager = LinearLayoutManager(context)
        }

        if (typeAppearacne == "flower") {
            val flowerColmn = SettingManager.getNoFlowerColumn(context)
            val flowerCommitHistoryAdapter =
                FlowerBedCommitHistoryAdapter { position, commitHistory ->
                    viewModel.setSelectedCommitHistory(commitHistory)
                }
            binding.rvCommitHistory.adapter = flowerCommitHistoryAdapter
            binding.rvCommitHistory.layoutManager = GridLayoutManager(context, flowerColmn)
        }

        viewModel.setAppearacneType(typeAppearacne)

        val bottomSheet: ConstraintLayout by lazy { binding.layBottomSheet }
        val sheetBehavior: BottomSheetBehavior<ConstraintLayout> =
            BottomSheetBehavior.from(bottomSheet)
        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        viewModel.setCollapsed()
                        binding.viewBackground.setVisibleIf(false)
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        viewModel.setExpanded()
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (slideOffset > 0) {
                    binding.viewBackground.setVisibleIf(true)
                }
            }
        })

        viewModel.contributions.observe(viewLifecycleOwner, { contributions ->
            if (!contributions.isNullOrEmpty()) {
                viewModel.setState(CommitHistoryViewModel.ViewState.RESULT)
            }
        })

        viewModel.requestCollapse.observe(viewLifecycleOwner, { requestCollapse ->
            if (requestCollapse) {
                sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        })

        binding.tvNickname.setOnClickListener {
            if (sheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else if (sheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        binding.ivExpand.setOnClickListener {
            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.ivLess.setOnClickListener {
            sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.btnCapture.setOnClickListener {
            it.visibility = View.GONE

            captureScreenShotUsingCanvas()
            captureScreenShotUsingPixelCopy()

            binding.layFlash.visibility = View.VISIBLE

            val fade = AlphaAnimation(1.0f, 0.0f)
            fade.duration = 500
            fade.setAnimationListener(AlphaAnimationListener(binding.layFlash))

            binding.layFlash.startAnimation(fade)

            it.visibility = View.VISIBLE
        }

        binding.btnShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "This is test")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        binding.btnAccountConfirm.setOnClickListener { view ->
            viewModel.setRequestCollapse()

            val imm =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            view.clearFocus()

            val githubNicknameValue = viewModel.githubNickName.value

            if (githubNicknameValue.isNullOrEmpty()) {
                Toast.makeText(context, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                if (SettingManager.getKeepGithubNickname(context)) {
                    SettingManager.setGithubNickname(context, githubNicknameValue)
                }
                viewModel.setState(CommitHistoryViewModel.ViewState.LOADING)
                viewModel.getContributions(githubNicknameValue)
            }
        }

        binding.tvChangeNickname.setOnClickListener {
            viewModel.clearGithubNickName()
            viewModel.setState(CommitHistoryViewModel.ViewState.INIT)
        }

        binding.tvExitApp.setOnClickListener {
            Log.i("buttonTest", "setOnClickListener tvExitApp")
            AlertDialog.Builder(requireContext())
                .setMessage("Would you like to exit the app")
                .setPositiveButton("OK") { dialog, which -> exitProcess(0) }
                .setNegativeButton("Cancel") { dialog, which -> dialog?.dismiss() }.create().show()
        }

        Log.i("keepNickname", "keep? ${SettingManager.getKeepGithubNickname(context)}")
        if (SettingManager.getKeepGithubNickname(context)) {
            val nickname = SettingManager.getGithubNickname(context)
            Log.i("keepNickname", "nickname? $nickname")

            if (nickname.isNotEmpty()) {
                Log.i("keepNickname", "nickname isNotEmpty")
                viewModel.setGithubNickname(nickname)
                viewModel.setState(CommitHistoryViewModel.ViewState.LOADING)
                viewModel.getContributions(nickname)
            }
        }

        return binding.root
    }

    private fun captureScreenShotUsingCanvas() {

        Log.i("fileText", "captureScreenShotUsingCanvas fileCaptureTest")

        val date = Date()
        val now = DateFormat.format("yyyy-MM-dd_hh:mm:ss", date)

        try {

            val fPath = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

            val captureView = binding.layCommitHistory
            val bitmap =
                Bitmap.createBitmap(captureView.width, captureView.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            captureView.draw(canvas)

            val imageFile = File(fPath, "canvas_screenshot_$now.png")
            val outputStream = FileOutputStream(imageFile)
            val quality = 100
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, outputStream)
            outputStream.flush()
            outputStream.close()

            // Save image in Gallery
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                val contentResolver = context?.contentResolver ?: return
                val contentValues = ContentValues()
                contentValues.put(
                    MediaStore.MediaColumns.DISPLAY_NAME,
                    "canvas_screenshot_$now.png"
                )
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                contentValues.put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    "DCIM/" + "OneCommitOneDay"
                )

                val imageUri = contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues
                ) ?: return
                val pictureOutputStream = contentResolver.openOutputStream(imageUri) ?: return
                bitmap.compress(Bitmap.CompressFormat.PNG, quality, pictureOutputStream)
                pictureOutputStream.flush()
                pictureOutputStream.close()
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    private fun captureScreenShotUsingPixelCopy() {

        activity?.window?.let { window ->

            Log.i("fileText", "captureScreenShotUsingPixelCopy fileCaptureTest")

            val date = Date()
            val now = DateFormat.format("yyyy-MM-dd_hh:mm:ss", date)

            val fPath = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

            val captureView = binding.layCommitHistory
            val bitmap =
                Bitmap.createBitmap(captureView.width, captureView.height, Bitmap.Config.ARGB_8888)
            val locationOfViewInWindow = IntArray(2)
            captureView.getLocationInWindow(locationOfViewInWindow)

            try {
                val looper = Looper.myLooper() ?: return
                PixelCopy.request(
                    window,
                    Rect(
                        locationOfViewInWindow[0],
                        locationOfViewInWindow[1],
                        locationOfViewInWindow[0] + captureView.width,
                        locationOfViewInWindow[1] + captureView.height
                    ),
                    bitmap,
                    { copyResult ->
                        if (copyResult == PixelCopy.SUCCESS) {
                            val imageFile = File(fPath, "PixelCopy_screenshot_$now.png")
                            val outputStream = FileOutputStream(imageFile)
                            val quality = 100
                            bitmap.compress(Bitmap.CompressFormat.PNG, quality, outputStream)
                            outputStream.flush()
                            outputStream.close()
                        }
                    },
                    Handler(looper)
                )

            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }

        }
    }
}