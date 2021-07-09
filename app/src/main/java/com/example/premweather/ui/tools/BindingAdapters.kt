package com.example.premweather.ui.tools

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.premweather.ui.Status
import android.text.format.DateFormat
import java.util.*

@BindingAdapter("timeFormat")
fun timeFormat(view: TextView, time: Long) {
    view.text = DateFormat.format("EEE, d MMMM yyyy h:mm a", time * 1000L)
}

@BindingAdapter("dateFormat")
fun dateFormat(view: TextView, date: Long) {
    view.text = DateFormat.format("EEE, dd MMM yyyy", date * 1000L)
}

@BindingAdapter("weatherIcon")
fun setWeatherIcon(imageView: ImageView, weatherIcon: String?) {
    val icon = weatherIcon?: "10d"
    val imageUrl = "https://openweathermap.org/img/wn/$icon@2x.png"

    Glide.with(imageView.context)
    .load(imageUrl)
        .into(imageView)
}

@BindingAdapter("loadingVisibility")
fun setVisibleOnLoading(view: View, status: Status) {
    view.visibility = when (status) {
        Status.Loading -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("successVisibility")
fun setVisibleOnSuccess(view: View, status: Status) {
    view.visibility = when (status) {
        Status.Success -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("noDataVisibility")
fun setVisibleOnNoData(view: View, status: Status) {
    view.visibility = when (status) {
        Status.Idle -> View.VISIBLE
        Status.Failed -> View.VISIBLE
        else -> View.GONE
    }
}