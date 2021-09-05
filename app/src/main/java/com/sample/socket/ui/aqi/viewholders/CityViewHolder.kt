package com.sample.socket.ui.aqi.viewholders

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.library.baseAdapters.BR

class CityViewHolder(viewBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewBinding.root) {
    private val binding = viewBinding

    @SuppressLint("SetTextI18n")
    fun bind(obj: Any) {
        binding.setVariable(BR.item, obj);
        binding.executePendingBindings();
    }
}