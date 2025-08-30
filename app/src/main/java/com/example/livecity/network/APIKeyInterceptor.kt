package com.example.livecity.network

import com.google.maps.android.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class APIKeyInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val currentUrl = chain.request().url
        val newUrl = currentUrl.newBuilder().addQueryParameter("key", "YOUR_API_KEY").build()
        val currentRequest = chain.request().newBuilder()
        val newRequest = currentRequest.url(newUrl).build()

        return chain.proceed(newRequest)
    }
}