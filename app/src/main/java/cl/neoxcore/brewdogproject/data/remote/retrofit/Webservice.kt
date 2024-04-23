package cl.neoxcore.brewdogproject.data.remote.retrofit

import cl.neoxcore.brewdogproject.data.remote.model.RemoteBeer
import retrofit2.http.GET

interface Webservice {
    @GET("beers.json")
    suspend fun getBeers(): List<RemoteBeer>

}