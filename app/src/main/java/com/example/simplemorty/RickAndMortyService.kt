package com.example.simplemorty
import retrofit2.Call
import retrofit2.http.GET
//interface that implements retro fit call and returns out response (JSON)
interface RickAndMortyService {
    //gets character
    @GET("character/2")
    //we return an object that we defined
    fun getCharacterByID(): Call<GetCharacterByIdResponse>
}