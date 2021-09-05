package com.sample.socket.ui.aqi.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.sample.socket.R
import com.sample.socket.datalib.models.socket.SocketDataResponse
import com.sample.socket.ui.aqi.fragments.MainFragment
import com.sample.socket.ui.aqi.viewholders.CityViewHolder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@SuppressLint("NotifyDataSetChanged")
class CityAdapter @Inject constructor(val fragment: MainFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data:MutableList<SocketDataResponse> = arrayListOf()
    var context:Context?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding:ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_city, parent,false)
        binding.setVariable(BR.activity, fragment.activity);
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CityViewHolder).bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateList(data: List<SocketDataResponse>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }
}

@Module
@InstallIn(FragmentComponent::class)
internal object Module {
    @Provides
    @FragmentScoped
    fun bindActivity(fragment: Fragment): MainFragment = fragment as MainFragment
}