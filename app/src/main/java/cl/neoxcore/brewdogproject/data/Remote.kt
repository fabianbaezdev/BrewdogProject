package cl.neoxcore.brewdogproject.data

import cl.neoxcore.brewdogproject.data.remote.model.RemoteBeer

interface Remote {
    suspend fun getBeers(): List<RemoteBeer>
}