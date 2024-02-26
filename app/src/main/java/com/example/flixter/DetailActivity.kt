package com.example.flixter

import PersonService
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

class DetailActivity : AppCompatActivity() {
    private lateinit var personImageView: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var knownForTextView: TextView
    private lateinit var descriptionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_details)

        // Find views
        personImageView = findViewById(R.id.personImage)
        nameTextView = findViewById(R.id.personName)
        knownForTextView = findViewById(R.id.personKnownFor)
        descriptionTextView = findViewById(R.id.personDescription)

        // Get person ID from intent
        val personId = intent.getIntExtra("PERSON_ID", 0)

        // Make API call to get person details
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(PersonService::class.java)
        val call = service.getPersonDetails(personId, API_KEY)

        call.enqueue(object : Callback<PersonDetails> {
            override fun onResponse(
                call: Call<PersonDetails>,
                response: Response<PersonDetails>
            ) {
                if (response.isSuccessful) {
                    val personDetails = response.body()

                    // Set data to views
                    nameTextView.text = personDetails?.name
                    knownForTextView.text = personDetails?.knownFor
                    descriptionTextView.text = personDetails?.description

                    Glide.with(this@DetailActivity)
                        .load("https://image.tmdb.org/t/p/w500/${personDetails?.profilePath}")
                        .into(personImageView)
                }
            }

            override fun onFailure(call: Call<PersonDetails>, t: Throwable) {
                // Handle failure
            }
        })
    }
}
