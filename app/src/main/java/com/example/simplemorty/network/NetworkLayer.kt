package com.example.simplemorty.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkLayer {
    //create moshi instance to deserialize json from GET call
    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    //create retrofit instance
    val retrofit : Retrofit = Retrofit.Builder()
        //base url for rest api calls
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
    //lazy means our service instance is initialzed only on the first call.
    val rickAndMortyService : RickAndMortyService by lazy {
        retrofit.create(RickAndMortyService::class.java)
    }

    val apiClient = ApiClient(rickAndMortyService)
}