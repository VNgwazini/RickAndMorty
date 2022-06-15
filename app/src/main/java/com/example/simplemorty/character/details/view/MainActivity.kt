package com.example.simplemorty.character.details.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.simplemorty.R
import com.example.simplemorty.character.details.view.epoxy.CharacterDetailsEpoxyController
import com.example.simplemorty.character.details.viewmodel.SharedViewModel
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }

    private val epoxyController = CharacterDetailsEpoxyController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val randomInt = Random.nextInt(826)
        //refresh character
        viewModel.refreshCharacter(randomInt)
        viewModel.characterByIdLiveData.observe(this){ response ->
            epoxyController.characterResponse = response
            if(response == null){
                Toast.makeText(this@MainActivity, "Unsuccessful Network Call!", Toast.LENGTH_SHORT).show()
                return@observe
            }
        }
        viewModel.refreshCharacter(randomInt)

        //get out layout
        val epoxyRecyclerView = findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }
}