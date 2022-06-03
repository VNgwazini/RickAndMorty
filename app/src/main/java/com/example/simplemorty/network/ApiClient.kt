package com.example.simplemorty.network

import com.example.simplemorty.network.response.GetCharacterByIdResponse
import retrofit2.Response

class ApiClient(
    private val rickAndMortyService: RickAndMortyService
) {
    //use suspendable function to make network call in coroutine
    suspend fun getCharacterById(characterId : Int): SimpleResponse<GetCharacterByIdResponse> {
        return safeApiCal { rickAndMortyService.getCharacterByID(characterId) }
    }

    private inline fun <T> safeApiCal(apiCall: () -> Response<T>) : SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        }catch (e: Exception){
            SimpleResponse.failure(e)
        }
    }
}