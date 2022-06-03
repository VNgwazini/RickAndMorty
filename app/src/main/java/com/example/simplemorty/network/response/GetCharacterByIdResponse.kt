package com.example.simplemorty.network.response

import com.example.simplemorty.viewmodel.Location
import com.example.simplemorty.viewmodel.Origin

data class GetCharacterByIdResponse(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)