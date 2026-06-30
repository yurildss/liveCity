package com.example.livecity.util

import com.example.livecity.R
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class IconByNameTest {

    @Test
    fun getIconResByName_withDangerIcon_returnsCorrectResourceId() {
        val result = getIconResByName("ic_danger")
        assertEquals(R.drawable.dangerous, result)
    }

    @Test
    fun getIconResByName_withBuildingIcon_returnsCorrectResourceId() {
        val result = getIconResByName("ic_building")
        assertEquals(R.drawable.build_50dp_ea3323_fill0_wght400_grad0_opsz48, result)
    }

    @Test
    fun getIconResByName_withCarCrashIcon_returnsCorrectResourceId() {
        val result = getIconResByName("ic_car_crash")
        assertEquals(R.drawable.car_crash_50dp_ea3323_fill0_wght400_grad0_opsz48, result)
    }

    @Test
    fun getIconResByName_withRainSnowStormIcon_returnsCorrectResourceId() {
        val result = getIconResByName("ic_rain_snow_storm")
        assertEquals(R.drawable.thunderstorm_50dp_ea3323_fill0_wght400_grad0_opsz48, result)
    }

    @Test
    fun getIconResByName_withInvalidIcon_throwsException() {
        assertFailsWith<IllegalArgumentException> {
            getIconResByName("ic_invalid")
        }
    }

    @Test
    fun getIconResByName_withEmptyString_throwsException() {
        assertFailsWith<IllegalArgumentException> {
            getIconResByName("")
        }
    }

    @Test
    fun getIconResByName_withNullString_throwsException() {
        assertFailsWith<IllegalArgumentException> {
            getIconResByName("ic_unknown")
        }
    }
}

