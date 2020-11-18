package com.har.habitforyou.ui.photos

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.har.habitforyou.R
import com.har.habitforyou.databinding.FragmentPhotosBinding
import com.har.habitforyou.ui.photos.dummy.DummyContent
import com.har.habitforyou.ui.photos.photoItem.PhotoItemRecyclerViewAdapter

/**
 * A fragment representing a list of Items.
 */
class PhotosFragment : Fragment() {

    companion object {
        fun newInstance() = PhotosFragment()
    }

    private var _binding: FragmentPhotosBinding? = null
    private val binding get() = _binding ?: throw NullPointerException("FragmentPhotoBinging is Null")
    private lateinit var viewModel: PhotosFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photos, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PhotosFragmentViewModel::class.java)
        binding.viewModel = viewModel

        val photosPagerAdapter = PhotosPagerAdapter(this)
        binding.vpPhoto.adapter = photosPagerAdapter
        binding.vpPhoto.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.tlPhoto, binding.vpPhoto) { tab, position ->
            tab.text = getString(photosPagerAdapter.getTitleResId(position))
            tab.setIcon(photosPagerAdapter.getIconResId(position))
            tab.customView = photosPagerAdapter.getView(position)
        }.attach()
    }
}