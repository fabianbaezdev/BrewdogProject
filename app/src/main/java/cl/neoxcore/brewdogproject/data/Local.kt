package cl.neoxcore.brewdogproject.data

import cl.neoxcore.brewdogproject.data.local.model.LocalBeer
import kotlinx.coroutines.flow.Flow

interface Local {
    suspend fun storeBeers(values: List<LocalBeer>)
    suspend fun getBeers(): Flow<List<LocalBeer>>
    suspend fun getBeer(id:String): Flow<LocalBeer>
}