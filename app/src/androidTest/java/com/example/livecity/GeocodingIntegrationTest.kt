package com.example.livecity

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.livecity.network.GeoCodingApi
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GeocodingIntegrationTest {
    @Test
    fun should_call_google_geo_code_api() = runBlocking {
        val response = GeoCodingApi.geocodingService
            .getAddressByGeo("-12.2456,-38.9647")
        assertTrue(response.status == "OK")
    }

    @Test
    fun should_formatted_address_be_r_catu() = runBlocking {
        val response = GeoCodingApi.geocodingService
            .getAddressByGeo("-12.2456,-38.9647")

        assertEquals(
            "R. Catu, 237A - Centro, Feira de Santana - BA, 44020-015, Brazil",
            response.results[0].formattedAddress
        )
    }

}