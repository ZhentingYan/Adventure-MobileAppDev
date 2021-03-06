package com.tongjisse.adventure.data.network.provider

import okhttp3.Interceptor

fun makeAddSecurityQueryInterceptor() = Interceptor { chain ->
    val originalRequest = chain.request()
    val url = originalRequest.url().newBuilder()
            .addQueryParameter("key", "2ae3d9389f30a91d1887b635c456bee5")
            .build()
    val request = originalRequest
            .newBuilder()
            .url(url)
            .build()
    chain.proceed(request)
}