package cl.neoxcore.brewdogproject.domain

import cl.neoxcore.brewdogproject.domain.model.Beer
import cl.neoxcore.brewdogproject.factory.BeerFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class GetBeersUseCaseTest{
    private val repository = mockk<Repository>()
    private val useCase = GetBeersUseCase(repository)

    @Test
    fun `when calls 'execute', the returns Beers`() = runBlocking {
        val beers = listOf(BeerFactory.makeBeer())
        stubRepository(beers)

        val flow = useCase.execute()

        flow.collect { result ->
            assertEquals(beers, result)
        }
        coVerify { repository.getBeers() }
    }

    private fun stubRepository(beers: List<Beer>) {
        coEvery { repository.getBeers() } returns flow { emit(beers) }
    }
}