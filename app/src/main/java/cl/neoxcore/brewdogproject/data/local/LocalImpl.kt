package cl.neoxcore.brewdogproject.data.local

import cl.neoxcore.brewdogproject.data.Local
import cl.neoxcore.brewdogproject.data.local.database.DatabaseBuilder
import cl.neoxcore.brewdogproject.data.local.model.LocalBeer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalImpl @Inject constructor(
    private val databaseBuilder: DatabaseBuilder
) : Local {
    override suspend fun storeBeers(values: List<LocalBeer>) {
        databaseBuilder.beerDao().insertAll(values)
    }

    override suspend fun getBeers(): Flow<List<LocalBeer>> = flow {
        val beers = databaseBuilder.beerDao().getAll()
        emit(beers)
    }

    override suspend fun getBeer(id: String): Flow<LocalBeer> = flow {
        val beer = databaseBuilder.beerDao().getById(id)
        emit(beer)
    }
}