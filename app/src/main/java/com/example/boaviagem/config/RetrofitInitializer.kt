package com.example.boaviagem.config

import com.example.boaviagem.api.IDataService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    private val retrofit: Retrofit =  Retrofit.Builder()
        .baseUrl("https://api-rest-boa-viagem022.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun boaViagemService() : IDataService {
        return retrofit.create(IDataService::class.java)
    }
}