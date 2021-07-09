package com.example.premweather.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.recyclerview.widget.RecyclerView
import com.example.premweather.R
import com.example.premweather.databinding.ItemWeatherForecastBinding
import com.example.premweather.domain.WeatherState

class WeatherForecastItemAdapter(var weatherStateList: MutableList<WeatherState>) :
    RecyclerView.Adapter<WeatherForecastItemAdapter.WeatherForecastViewHolder>() {

    class WeatherForecastViewHolder(
        val itemBinding: ItemWeatherForecastBinding
    ) : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherForecastViewHolder {
        val itemBinding: ItemWeatherForecastBinding = inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_weather_forecast,
            parent,
            false
        )
        return WeatherForecastViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: WeatherForecastViewHolder, position: Int) {
        holder.itemBinding.weatherState = weatherStateList[position]
    }

    override fun getItemCount(): Int {
        return weatherStateList.size
    }

    fun update(newList: List<WeatherState>) {
        weatherStateList.clear()
        weatherStateList.addAll(newList)
        notifyDataSetChanged()
    }
}