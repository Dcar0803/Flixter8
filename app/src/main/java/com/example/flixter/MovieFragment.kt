package com.example.flixter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers

private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

class MovieFragment : Fragment(), OnListFragmentInteractionListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = context // Use requireContext() if targeting API level 23 or higher
        recyclerView.layoutManager = LinearLayoutManager(context)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        val client = AsyncHTTPClient()
        val params = RequestParams()
        params["api_key"] = API_KEY

        client.get(
            "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed",
            params,
            object : JsonHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                    progressBar.hide()
                    json?.let {
                        val moviesJSON = it.getJSONObject("results").toString()
                        val gson = Gson()
                        val type = object : TypeToken<List<Movie>>() {}.type
                        val movies: List<Movie> = gson.fromJson(moviesJSON, type)
                        recyclerView.adapter = MovieAdapter(movies, this@MovieFragment)
                    }
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    response: String?,
                    throwable: Throwable?
                ) {
                    progressBar.hide()
                    Log.e("com.example.flixter.MovieFragment", "Failed to fetch movies", throwable)
                    Toast.makeText(context, "Failed to fetch movies", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    override fun onItemClick(item: Movie) {
        Toast.makeText(context, "Clicked on: ${item.title}", Toast.LENGTH_SHORT).show()
    }

    // Inner classes and interfaces should be declared within the scope of the outer class
    class RequestParams {
        private val params = mutableMapOf<String, String>()

        operator fun set(key: String, value: String) {
            params[key] = value
        }

        fun toMap(): Map<String, String> {
            return params
        }
    }

    class AsyncHTTPClient {
        fun get(
            url: String,
            params: RequestParams,
            jsonHttpResponseHandler: JsonHttpResponseHandler
        ) {
            // Implement HTTP GET request logic here
            // This is just a placeholder
            // You should use a proper HTTP client library like Retrofit or Volley
            // to perform the actual HTTP request
            // and invoke appropriate methods on jsonHttpResponseHandler in the success and failure cases
        }
    }

    // Extension function to JsonHttpResponseHandler.JSON to mock getJSONObject method
    private fun JsonHttpResponseHandler.JSON.getJSONObject(s: String): Any {
        // Placeholder implementation
        return Any() // Replace Any() with the actual value you want to return
    }
}
