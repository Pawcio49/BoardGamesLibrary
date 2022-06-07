package com.example.boardgameslibrary.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory


object RetrofitHelper {

    val baseUrl = "https://boardgamegeek.com/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(OkHttpClient())
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
    }
}