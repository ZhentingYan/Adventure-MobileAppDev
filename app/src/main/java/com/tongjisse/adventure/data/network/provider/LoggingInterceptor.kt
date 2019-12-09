package com.tongjisse.adventure.data.network.provider

import com.tongjisse.adventure.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor


fun makeLoggingInterceptor() = HttpLoggingInterceptor().apply {
    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
    else HttpLoggingInterceptor.Level.NONE
}