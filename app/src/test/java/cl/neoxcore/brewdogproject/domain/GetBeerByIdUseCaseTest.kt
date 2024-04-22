package cl.neoxcore.brewdogproject.domain

import cl.neoxcore.brewdogproject.domain.model.Beer
import cl.neoxcore.brewdogproject.factory.BeerFactory.makeBeer
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class GetBeerByIdUseCaseTest{
    private val repository = mockk<Repository>()
    private val useCase = GetBeerByIdUseCase(repository)

    @Test
    fun `when calls 'execute', the returns Beer`() = runBlocking {
        val beer = makeBeer()
        stubRepository(beer)

        val flow = useCase.execute("id")

        flow.collect { result ->
            assertEquals(beer, result)
        }
        coVerify { repository.getBeer("id") }
    }

    private fun stubRepository(beer: Beer) {
        coEvery { repository.getBeer("id") } returns flow { emit(beer) }
    }
}