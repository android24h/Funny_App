package com.ahfprogrammer.jetpack.myapplication.Retrofit

import com.ahfprogrammer.jetpack.myapplication.ApiService.ApiService
import com.ahfprogrammer.jetpack.myapplication.Util.Base_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api:ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(Base_URL.BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}