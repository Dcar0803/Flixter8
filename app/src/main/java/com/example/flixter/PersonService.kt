
import com.example.flixter.PersonDetails
import com.example.flixter.PersonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonService {

    @GET("person/popular")
    fun getPopularPersons(
        @Query("api_key") apiKey: String
    ): Call<PersonResponse>

    @GET("person/{person_id}")
    fun getPersonDetails(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String
    ): Call<PersonDetails>
}
