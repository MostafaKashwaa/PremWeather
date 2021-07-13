package com.example.premweather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.premweather.R
import com.example.premweather.cache.WeatherDatabase
import com.example.premweather.databinding.FragmentCurrentWeatherBinding

class CurrentWeatherFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentCurrentWeatherBinding>(
            inflater,
            R.layout.fragment_current_weather,
            container,
            false
        )
        val viewModel: WeatherViewModel by viewModels({ requireActivity() }) {
            WeatherViewModelFactory(
                WeatherDatabase.getInstance(requireActivity().applicationContext)
            )
        }

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }
}