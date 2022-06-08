package com.example.simplemorty.character.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplemorty.SharedRepository
import com.example.simplemorty.network.response.GetCharacterByIdResponse
import kotlinx.coroutines.launch

//MVVM structure
class SharedViewModel: ViewModel()  {
    private val repository = SharedRepository()

    //now create two live data objects that will report to observers.
    //first live data is mutable since it will need to be updatable via the observer
    //TODO make nullable for now, handle later
    private val _characterByIdLiveData = MutableLiveData<GetCharacterByIdResponse?>()
    //second live data is immutable since we only want to be able to view this data via the observer
    val characterByIdResponse: LiveData<GetCharacterByIdResponse?> = _characterByIdLiveData

    fun refreshCharacter(characterId: Int){
        //get new updated values
        viewModelScope.launch {
            val response = repository.getCharacterById(characterId )
            //now update object with values
            _characterByIdLiveData.postValue(response)
        }
    }
}