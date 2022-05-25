package com.example.simplemorty

import retrofit2.Response
//This is where the data lives after the network call
class SharedRepository {
    //since the api client uses a coroutine (suspend), we can only call it from a coroutine
    //we are returning the actual character object here,
    // not the response since this is where ACCESS the data
    //TODO - handle failed request, make object nullable for now
    suspend fun getCharacterById(characterId : Int): GetCharacterByIdResponse? {
        //this is where we trigger the actual call that we use to populate our object
        val request = NetworkLayer.apiClient.getCharacterById(characterId)

        //now handle our request (pass and fail)
        if(request.isSuccessful){
            //its possible that this request is null, but we don't want to access a null object,
            // so lets make it non-null
            return request.body()!!
        }
        else{
            return null
        }
    }
}