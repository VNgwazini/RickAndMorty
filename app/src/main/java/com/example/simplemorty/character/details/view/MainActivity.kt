package com.example.simplemorty.character.details.view

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.simplemorty.R
import com.example.simplemorty.character.details.viewmodel.SharedViewModel
import com.squareup.picasso.Picasso
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //added 'textView' as id to hello world text view from homescreen
        val tvName = findViewById<TextView>(R.id.tv_name)
        val tvGender = findViewById<TextView>(R.id.tv_gender)
        val tvLocation = findViewById<TextView>(R.id.tv_location)
        val tvOrigin = findViewById<TextView>(R.id.tv_origin)
//        val tvSpecies = findViewById<TextView>(R.id.tv_species)
        val tvStatus = findViewById<TextView>(R.id.tv_status)
//        val tvType = findViewById<TextView>(R.id.tv_type)
        val ivImage = findViewById<ImageView>(R.id.iv_image)

        val randomInt = Random.nextInt(826)
        //refresh character
        viewModel.refreshCharacter(randomInt)
        viewModel.characterByIdResponse.observe(this){ response ->
            if(response == null){
                Toast.makeText(this@MainActivity, "Unsuccessful Network Call!", Toast.LENGTH_SHORT).show()
                return@observe
            }
            else{
                val body = response!!
                val name = body.name
                val origin = body.origin.name
                val gender = body.gender
                val location = body.location.name
                val species = body.species
                val status = body.status + " - " + species
                val image = body.image
                //get image from url and feed into image view
                Picasso.get().load(image).into(ivImage);

                //change the value of the hello world text view to name's value instead
                tvName.text = name
                tvOrigin.text = origin
                tvGender.text = gender
                tvLocation.text = location
                tvStatus.text = status
            }
        }

        //enqueue lets us know when the call is done
        //asynchronous call will crash app from main thread so we use enqueue to report response/error
//        rickAndMortyService.getCharacterByID(randomInt).enqueue(object: Callback<GetCharacterByIdResponse>{
//            //we must implement pass and fail abstract methods for callbacks
//            override fun onResponse(call: Call<GetCharacterByIdResponse>, response: Response<GetCharacterByIdResponse>) {
//                Log.i("Main Activity", response.toString())
//                //confirm response is valid and pull value into var
//                //if response fails, toast, otherwise populate object
//                if(!response.isSuccessful){
//                    Toast.makeText(this@MainActivity, "Unsuccessful Network Call!", Toast.LENGTH_SHORT).show()
//                    return
//                }
//
//                val body = response.body()!!
//                val name = body.name
//                val origin = body.origin.name
//                val gender = body.gender
//                val location = body.location.name
//                val species = body.species
//                val status = body.status + " - " + species
//                val image = body.image
////                val type = body.type
//
////                //check gender to display corresponding icon
////                if(body.gender.equals("male", true)){
////
////                }
////                else{
////
////                }
//
//                Toast.makeText(this@MainActivity, "Successful Network Call!", Toast.LENGTH_SHORT).show()
//
//                //change the value of the hello world text view to name's value instead
//                tvName.text = name
//                tvOrigin.text = origin
//                tvGender.text = gender
//                tvLocation.text = location
////                tvSpecies.text = species
//                tvStatus.text = status
////                tvType.text = type
//                //get image from url and feed into image view
//                Picasso.get().load(image).into(ivImage);
//
//            }
//
//            override fun onFailure(call: Call<GetCharacterByIdResponse>, t: Throwable) {
//                // use the elivs-operator "?:" to say if not null use val, otherwise use this
//                    Log.i("Main Activity", t.message ?: "Null Message")
//            }
//
//        })
    }
}