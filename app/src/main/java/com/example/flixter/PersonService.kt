import com.example.flixter.PersonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PersonService {

    @GET("person/popular")
    fun getPopularPersons(
        @Query("api_key") apiKey: String
    ): Call<PersonResponse>
}
