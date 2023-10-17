package cl.neoxcore.brewdogproject.data

import cl.neoxcore.brewdogproject.domain.Repository
import cl.neoxcore.brewdogproject.domain.model.Beer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remote: Remote,
    private val local: Local,
    private val beerMapper: DataBeerMapper
) : Repository {
    override fun getBeers(): Flow<List<Beer>> = flow {
        local.getBeers().collect {
            if (it.isNotEmpty())
                emit(with(beerMapper) { it.localToDomain() })
            else {
                val remoteBeers = remote.getBeers()
                local.storeBeers(with(beerMapper) { remoteBeers.toLocal() })
                emit(with(beerMapper) { remoteBeers.remoteToDomain() })
            }
        }
    }
}