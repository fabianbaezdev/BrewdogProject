package cl.neoxcore.brewdogproject.domain

import cl.neoxcore.brewdogproject.domain.model.Beer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBeersUseCase @Inject constructor(private val repository: Repository){
    fun execute(): Flow<List<Beer>> = repository.getBeers()
}