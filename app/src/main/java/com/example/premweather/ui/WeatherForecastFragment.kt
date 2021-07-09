package com.example.premweather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.premweather.R
import com.example.premweather.databinding.FragmentWeatherForcastBinding

class WeatherForecastFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentWeatherForcastBinding>(
            inflater,
            R.layout.fragment_weather_forcast,
            container,
            false
        )
        val viewModel: WeatherViewModel by viewModels({ requireActivity() }) { defaultViewModelProviderFactory }
        binding.lifecycleOwner = viewLifecycleOwner
        val adapter = WeatherForecastItemAdapter(mutableListOf())
        binding.forecastRecycler.adapter = adapter

        viewModel.forecast.observe(requireActivity()) {
            adapter.update(it)
        }

        return binding.root
    }
}
