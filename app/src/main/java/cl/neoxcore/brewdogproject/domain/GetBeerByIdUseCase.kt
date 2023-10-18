package cl.neoxcore.brewdogproject.domain

import cl.neoxcore.brewdogproject.domain.model.Beer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBeerByIdUseCase @Inject constructor(private val repository: Repository){
    fun execute(id:String): Flow<Beer> = repository.getBeer(id)
}