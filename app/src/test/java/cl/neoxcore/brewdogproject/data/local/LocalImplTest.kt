package cl.neoxcore.brewdogproject.data.local

import cl.neoxcore.brewdogproject.data.local.database.DatabaseBuilder
import cl.neoxcore.brewdogproject.factory.BeerFactory
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class LocalImplTest {
    private val databaseBuilder = mockk<DatabaseBuilder>()
    private val local = LocalImpl(databaseBuilder)

    @Test
    fun `given LocalBeers, when getBeers, the return data`() = runBlocking {
        val localBeers = listOf(BeerFactory.makeLocalBeer())
        coEvery { databaseBuilder.beerDao().getAll() } returns localBeers

        val flow = local.getBeers()
        flow.collect { result ->
            assertEquals(localBeers, result)
        }
    }

    @Test
    fun `given LocalBeer, when getBeer, the return data`() = runBlocking {
        val localBeer = BeerFactory.makeLocalBeer()
        coEvery { databaseBuilder.beerDao().getById("id") } returns localBeer

        val flow = local.getBeer("id")
        flow.collect { result ->
            assertEquals(localBeer, result)
        }
    }
}