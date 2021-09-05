package com.sample.socket.utils

import android.annotation.SuppressLint
import androidx.databinding.BindingAdapter
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.sample.socket.R

@SuppressLint("DefaultLocale")
@BindingAdapter("textColor")
fun formatAqi(view: AppCompatTextView, aqiString: String?) {
    val aqi = aqiString?.toDouble()
    aqi?.let {
        if (0 <= aqi && aqi < 51)
            view.setTextColor(ContextCompat.getColor(view.context, R.color.aqi_good))
        else if (51 <= aqi && aqi < 101)
            view.setTextColor(
                ContextCompat.getColor(
                    view.context,
                    R.color.aqi_satisfactory
                )
            )
        else if (101 <= aqi && aqi < 201)
            view.setTextColor(ContextCompat.getColor(view.context, R.color.aqi_moderate))
        else if (201 <= aqi && aqi < 301)
            view.setTextColor(ContextCompat.getColor(view.context, R.color.aqi_poor))
        else if (301 <= aqi && aqi < 401)
            view.setTextColor(ContextCompat.getColor(view.context, R.color.aqi_v_poor))
        else
            view.setTextColor(ContextCompat.getColor(view.context, R.color.aqi_severe))

        view.text = String.format("%.2f", aqi)
    }
}

@BindingAdapter("format")
fun formatTime(view: AppCompatTextView, timestamp: Long?) {
    timestamp?.let {
        view.text = TimeUtils.timeInString(timestamp)
    }
}
