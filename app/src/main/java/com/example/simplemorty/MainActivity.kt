package com.example.simplemorty

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //added 'textView' as id to hello world text view from homescreen
        val textView = findViewById<TextView>(R.id.textView)

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
        rickAndMortyService.getCharacterByID(10).enqueue(object: Callback<GetCharacterByIdResponse>{
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
                Toast.makeText(this@MainActivity, "Successful Network Call!", Toast.LENGTH_SHORT).show()

                //change the value of the hello world text view to name's value instead
                textView.text = name
            }

            override fun onFailure(call: Call<GetCharacterByIdResponse>, t: Throwable) {
                // use the elivs-operator "?:" to say if not null use val, otherwise use this
                    Log.i("Main Activity", t.message ?: "Null Message")
            }

        })
    }
}