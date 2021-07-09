package com.example.premweather

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.premweather.databinding.ActivityMainBinding
import com.example.premweather.ui.Status
import com.example.premweather.ui.WeatherViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val viewModel: WeatherViewModel by viewModels { defaultViewModelProviderFactory }

        val navController = findNavController(R.id.navigation_host)
        binding.bottomNavigation.setupWithNavController(navController)

        binding.btnGo.setOnClickListener {
            hideSoftKeyboard(it)
            viewModel.loadWeatherForCity(binding.editSearch.text.toString())
            viewModel.loadForecast(binding.editSearch.text.toString())
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadWeatherForCity(binding.editSearch.text.toString())
        }
        viewModel.status.observe(this) {
            binding.swipeRefreshLayout.isRefreshing = when(it) {
                Status.Loading ->  true
                else -> false
            }
        }
    }

    private fun MainActivity.hideSoftKeyboard(currentFocus: View?) {
        if (currentFocus != null)
            (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}