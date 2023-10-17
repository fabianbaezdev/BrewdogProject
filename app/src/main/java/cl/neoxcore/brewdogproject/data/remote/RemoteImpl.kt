package cl.neoxcore.brewdogproject.data.remote

import cl.neoxcore.brewdogproject.data.Remote
import cl.neoxcore.brewdogproject.data.remote.model.RemoteBeer
import cl.neoxcore.brewdogproject.data.remote.retrofit.Webservice
import javax.inject.Inject

class RemoteImpl @Inject constructor(private val webservice: Webservice): Remote {
    override suspend fun getBeers(): List<RemoteBeer> = webservice.getBeers()
}