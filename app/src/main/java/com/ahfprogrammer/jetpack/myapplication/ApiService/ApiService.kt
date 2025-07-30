package com.ahfprogrammer.jetpack.myapplication.ApiService

import com.ahfprogrammer.jetpack.myapplication.data.MemesDataModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("get_memes")
    suspend fun getMemesList():Response<MemesDataModel>
}