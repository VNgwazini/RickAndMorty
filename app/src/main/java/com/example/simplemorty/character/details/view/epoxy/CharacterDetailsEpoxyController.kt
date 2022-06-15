package com.example.simplemorty.character.details.view.epoxy

import com.airbnb.epoxy.EpoxyController
import com.example.simplemorty.R
import com.example.simplemorty.databinding.ModelCharacterDetailsDataBinding
import com.example.simplemorty.databinding.ModelCharacterDetailsHeaderBinding
import com.example.simplemorty.databinding.ModelCharacterDetailsImageBinding
import com.example.simplemorty.network.response.GetCharacterByIdResponse
import com.squareup.picasso.Picasso

class CharacterDetailsEpoxyController:EpoxyController() {
    //loading view
    var isLoading: Boolean = true
        set(value){
            field = value
            if (field){
                requestModelBuild()
            }
        }

    var characterResponse: GetCharacterByIdResponse? = null
        set(value){
            field = value
            //got a response, no longer loading
            if (value != null){
                isLoading = false
                requestModelBuild()
            }
        }

    override fun buildModels() {
        if(isLoading){
            //show loading state
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        if(characterResponse == null){
            //TODO handle error state
            return
        }

        //add header model
        HeaderEpoxyModel(
            name = characterResponse!!.name,
            gender = characterResponse!!.gender,
        ).id("header  ").addTo(this)

        //add image model
        ImageEpoxyModel(
            imageUrl = characterResponse!!.image
        ).id("image").addTo(this)

        //add data models
        DataEpoxyModel(
            status = characterResponse!!.status,
            lastKnownLocation = "Last Known Location",
            location = characterResponse!!.location.name,
            firstSeenIn = "First Seen In",
            origin = characterResponse!!.origin.name,
        ).id("data").addTo(this)
    }

    data class DataEpoxyModel(
        val status: String,
        val lastKnownLocation: String,
        val location: String,
        val firstSeenIn: String,
        val origin: String
    ):ViewBindingKotlinModel<ModelCharacterDetailsDataBinding>(R.layout.model_character_details_data){

        override fun ModelCharacterDetailsDataBinding.bind() {
            tvStatus.text = status
            tvLastKnownLocation.text = lastKnownLocation
            tvLocation.text = location
            tvFirstSeenIn.text = firstSeenIn
            tvOrigin.text = origin
        }
    }

    data class ImageEpoxyModel(val imageUrl: String): ViewBindingKotlinModel<ModelCharacterDetailsImageBinding>(R.layout.model_character_details_image){

        override fun ModelCharacterDetailsImageBinding.bind(){
            Picasso.get().load(imageUrl).into(ivImage);
        }
    }

    data class HeaderEpoxyModel(
        val name: String,
        val gender: String,
    ): ViewBindingKotlinModel<ModelCharacterDetailsHeaderBinding>(R.layout.model_character_details_header) {

        override fun ModelCharacterDetailsHeaderBinding.bind(){
            tvName.text = name
            tvGender.text = gender
        }
    }
}