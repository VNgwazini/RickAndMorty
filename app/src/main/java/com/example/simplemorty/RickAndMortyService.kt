package com.example.simplemorty
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

//interface that implements retro fit call and returns out response (JSON)
interface RickAndMortyService {
    //gets character of id that is passed
    @GET("character/{character-id}")
    //we return an object that we defined that corresponds to the id passed
    //@Path replaces character-id with an actual value, in this case, an Int
    fun getCharacterByID(
        @Path("character-id") characterId: Int
    ): Call<GetCharacterByIdResponse>
}