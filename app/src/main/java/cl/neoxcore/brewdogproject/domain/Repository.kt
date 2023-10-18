package cl.neoxcore.brewdogproject.domain

import cl.neoxcore.brewdogproject.domain.model.Beer
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getBeers(): Flow<List<Beer>>
    fun getBeer(id: String): Flow<Beer>
}
