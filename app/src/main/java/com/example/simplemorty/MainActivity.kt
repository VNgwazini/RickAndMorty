package com.example.simplemorty

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.picasso.Picasso
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        //create moshi instance to deserialize json from GET call
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val retrofit : Retrofit = Retrofit.Builder()
                //base url for rest api calls
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val rickAndMortyService : RickAndMortyService = retrofit.create(RickAndMortyService::class.java)
        //enqueue lets us know when the call is done
        //asynchronous call will crash app from main thread so we use enqueue to report response/error
        rickAndMortyService.getCharacterByID(randomInt).enqueue(object: Callback<GetCharacterByIdResponse>{
            //we must implement pass and fail abstract methods for callbacks
            override fun onResponse(call: Call<GetCharacterByIdResponse>, response: Response<GetCharacterByIdResponse>) {
                Log.i("Main Activity", response.toString())
                //confirm response is valid and pull value into var
                //if response fails, toast, otherwise populate object
                if(!response.isSuccessful){
                    Toast.makeText(this@MainActivity, "Unsuccessful Network Call!", Toast.LENGTH_SHORT).show()
                    return
                }

                val body = response.body()!!
                val name = body.name
                val origin = body.origin.name
                val gender = body.gender
                val location = body.location.name
                val species = body.species
                val status = body.status + " - " + species
                val image = body.image
//                val type = body.type

                Toast.makeText(this@MainActivity, "Successful Network Call!", Toast.LENGTH_SHORT).show()

                //change the value of the hello world text view to name's value instead
                tvName.text = name
                tvOrigin.text = origin
                tvGender.text = gender
                tvLocation.text = location
//                tvSpecies.text = species
                tvStatus.text = status
//                tvType.text = type
                //get image from url and feed into image view
                Picasso.get().load(image).into(ivImage);

            }

            override fun onFailure(call: Call<GetCharacterByIdResponse>, t: Throwable) {
                // use the elivs-operator "?:" to say if not null use val, otherwise use this
                    Log.i("Main Activity", t.message ?: "Null Message")
            }

        })
    }
}