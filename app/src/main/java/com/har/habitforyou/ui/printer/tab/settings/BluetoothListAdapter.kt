package com.har.habitforyou.ui.printer.tab.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.har.habitforyou.base.ItemSelectListener
import com.har.habitforyou.databinding.ItemBluetoothScannedBinding

class BluetoothListAdapter: RecyclerView.Adapter<BluetoothListItemViewHolder>(), ItemSelectListener {

    private val _viewModels: MutableList<BluetoothListItemViewModel> = mutableListOf()
    private var _bluetoothSelectListener: BluetoothSelectListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BluetoothListItemViewHolder {
        val binding = ItemBluetoothScannedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BluetoothListItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return _viewModels.size
    }

    override fun onBindViewHolder(holder: BluetoothListItemViewHolder, position: Int) {
        val viewModel = _viewModels[position]
        holder.bind(viewModel, this)
    }

    fun setViewModels(viewModels: List<BluetoothListItemViewModel>) {
        this._viewModels.clear()
        if (viewModels.isNotEmpty()) {
            this._viewModels.addAll(viewModels)
        }

        notifyDataSetChanged()
    }

    fun setBluetoothSelectListener(bluetoothSelectListener: BluetoothSelectListener) {
        this._bluetoothSelectListener = bluetoothSelectListener
    }

    override fun onSelect(view: View, position: Int) {
        _bluetoothSelectListener?.onSelect(position, _viewModels[position].bluetoothDevice)
    }
}