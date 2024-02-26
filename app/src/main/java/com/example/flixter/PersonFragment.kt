package com.example.flixter

import PersonService
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flixter.databinding.FragmentPersonListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

class PersonFragment : Fragment(), OnPersonItemClickListener {

    private lateinit var personAdapter: PersonAdapter
    private var _binding: FragmentPersonListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPersonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        personAdapter = PersonAdapter(emptyList(), this)

        binding.personListRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = personAdapter
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(PersonService::class.java)

        val call = service.getPopularPersons(API_KEY)
        call.enqueue(object : Callback<PersonResponse> {
            override fun onResponse(call: Call<PersonResponse>, response: Response<PersonResponse>) {
                if (response.isSuccessful) {
                    val persons = response.body()?.persons ?: emptyList()
                    personAdapter.setData(persons)
                }
            }

            override fun onFailure(call: Call<PersonResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Failed to fetch persons", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPersonItemClick(person: Person) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra("PERSON_ID", person.id)
        startActivity(intent)
    }
}
