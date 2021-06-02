package com.har.habitforyou.ui.photos.gallery

import com.har.habitforyou.R
import com.har.habitforyou.base.BaseBindingDialog
import com.har.habitforyou.databinding.DialogPhotoBinding

class PhotoDialog: BaseBindingDialog<DialogPhotoBinding, PhotoDialogViewModel>() {

    override fun getContentLayoutId(): Int {
        return R.layout.dialog_photo
    }

    override fun getViewModelClass(): Class<PhotoDialogViewModel> {
        return PhotoDialogViewModel::class.java
    }

    override fun initBinding(viewModel: PhotoDialogViewModel, binding: DialogPhotoBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    companion object {
        @JvmStatic
        fun instance(): PhotoDialog {
            return PhotoDialog()
        }
    }
}