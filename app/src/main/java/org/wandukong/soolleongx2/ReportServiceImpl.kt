package org.wandukong.soolleongx2

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ReportServiceImpl {
    private const val BASE_URL = "http://15.164.67.58/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: ReportService = retrofit.create(ReportService::class.java)
}