package com.example.livecity.network

import android.util.Log
import com.google.maps.android.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class APIKeyInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val currentUrl = chain.request().url
        val newUrl = currentUrl.newBuilder().addQueryParameter("key", "").build()
        val currentRequest = chain.request().newBuilder()
        val newRequest = currentRequest.url(newUrl).build()
        Log.d("API_CALL", "Request URL: $newRequest")
        return chain.proceed(newRequest)
    }
}