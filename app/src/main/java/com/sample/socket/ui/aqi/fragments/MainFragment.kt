package com.sample.socket.ui.aqi.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.socket.R
import com.sample.socket.base.extensions.remove
import com.sample.socket.base.extensions.show
import com.sample.socket.base.fragments.BaseAndroidDataFlowViewModelFragment
import com.sample.socket.databinding.FragmentMainBinding
import com.sample.socket.ui.aqi.adapters.CityAdapter
import com.sample.socket.ui.aqi.viewmodels.AqiViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment: BaseAndroidDataFlowViewModelFragment<FragmentMainBinding, AqiViewModel>(
    FragmentMainBinding::inflate, AqiViewModel::class, false){

    @Inject
    lateinit var cityAdapter: CityAdapter

    @SuppressLint("SetTextI18n")
    override fun FragmentMainBinding.setupViews(savedInstanceState: Bundle?) {
        (activity as (AppCompatActivity)).supportActionBar?.let {
            it.setHomeButtonEnabled(false)
            it.setDisplayHomeAsUpEnabled(false)
        }
        activity?.title = "AQI"

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        setRecyclerView(layoutManager, cityAdapter)
        progressBar.show()
    }

    override fun FragmentMainBinding.observeViewModel(vm: AqiViewModel) {
        vm.getAQI().observe(viewLifecycleOwner, {
            progressBar.remove()
            cityAdapter.updateList(it)
        })
    }

    private fun FragmentMainBinding.setRecyclerView(manager: RecyclerView.LayoutManager, mAdapter : RecyclerView.Adapter<*> ){
        cityList.apply {
            itemAnimator = DefaultItemAnimator()
            layoutManager = manager
            adapter = mAdapter
        }
    }
}