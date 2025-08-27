package com.example.livecity.util

import com.example.livecity.R

fun getIconResByName(name: String): Int {
    return when (name) {
        "ic_danger" -> R.drawable.dangerous
        "ic_building" -> R.drawable.build_50dp_ea3323_fill0_wght400_grad0_opsz48
        "ic_car_crash" -> R.drawable.car_crash_50dp_ea3323_fill0_wght400_grad0_opsz48
        "ic_rain_snow_storm" -> R.drawable.thunderstorm_50dp_ea3323_fill0_wght400_grad0_opsz48
        else -> throw IllegalArgumentException("Invalid icon name: $name")
    }
}
