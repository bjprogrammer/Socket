package com.sample.socket.ui.aqi.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.Entry
import com.sample.socket.base.extensions.remove
import com.sample.socket.base.extensions.show
import com.sample.socket.base.fragments.BaseAndroidDataFlowViewModelFragment
import com.sample.socket.databinding.FragmentChildBinding
import com.sample.socket.ui.aqi.viewmodels.AqiViewModel
import com.sample.socket.utils.Bundle.CITY
import dagger.hilt.android.AndroidEntryPoint
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import android.graphics.Color
import androidx.core.content.ContextCompat
import android.graphics.DashPathEffect
import com.github.mikephil.charting.data.LineDataSet
import com.sample.socket.R

@AndroidEntryPoint
class ChildFragment: BaseAndroidDataFlowViewModelFragment<FragmentChildBinding, AqiViewModel>(
    FragmentChildBinding::inflate, AqiViewModel::class, false) {

    @SuppressLint("SetTextI18n")
    override fun FragmentChildBinding.setupViews(savedInstanceState: Bundle?) {
        progressBar.show()

        (activity as (AppCompatActivity)).supportActionBar?.let {
            it.setHomeButtonEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
        }
        requireArguments().getString(CITY)?.let {
            activity?.title = it
            getViewModel().getAqiDataHistory(it)
        }
    }

    override fun FragmentChildBinding.observeViewModel(vm: AqiViewModel) {
        vm.getAQIOfCity().observe(viewLifecycleOwner, {
            progressBar.remove()

            mChart.setTouchEnabled(true)
            mChart.setPinchZoom(true)
            val values = ArrayList<Entry>()
            for(index in it.indices){
                values.add(Entry(index.toFloat(), it[index].airQuality?.toFloat()!!))
            }

            var set: LineDataSet
            if (mChart.data != null && mChart.data.dataSetCount > 0) {
                set = mChart.data.getDataSetByIndex(0) as LineDataSet
                set.values = values

                mChart.data.notifyDataChanged()
                mChart.notifyDataSetChanged()
            } else {
                set = LineDataSet(values, "AQI History")
                set.setDrawIcons(false)
                set.enableDashedLine(10f, 5f, 0f)
                set.enableDashedHighlightLine(10f, 5f, 0f)
                set.color = Color.DKGRAY
                set.setCircleColor(Color.DKGRAY)
                set.lineWidth = 1f
                set.circleRadius = 3f
                set.setDrawCircleHole(false)
                set.valueTextSize = 9f
                set.setDrawFilled(true)
                set.formLineWidth = 1f
                set.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
                set.formSize = 15f
                set.fillDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.fade_blue)

                val dataSets: ArrayList<ILineDataSet> = ArrayList()
                dataSets.add(set)

                val data = LineData(dataSets)
                mChart.data = data
            }

            mChart.invalidate()
        })
    }
}