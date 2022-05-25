package com.example.simplemorty

import retrofit2.Response

class ApiClient(
    private val rickAndMortyService: RickAndMortyService
) {
    //use suspendable function to make network call in coroutine
    suspend fun getCharacterById(characterId : Int): Response<GetCharacterByIdResponse> {
        return rickAndMortyService.getCharacterByID(characterId)
    }
}