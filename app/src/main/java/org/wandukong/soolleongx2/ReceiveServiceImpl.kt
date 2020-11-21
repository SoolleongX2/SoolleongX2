package org.wandukong.soolleongx2

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ReceiveServiceImpl {
    private const val BASE_URL = "http://15.164.67.58:3002"

    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service : ReceiveService = retrofit.create(ReceiveService::class.java)
}