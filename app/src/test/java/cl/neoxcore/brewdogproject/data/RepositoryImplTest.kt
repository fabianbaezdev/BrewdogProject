package cl.neoxcore.brewdogproject.data

import cl.neoxcore.brewdogproject.factory.BeerFactory.makeBeer
import cl.neoxcore.brewdogproject.factory.BeerFactory.makeLocalBeer
import cl.neoxcore.brewdogproject.factory.BeerFactory.makeRemoteBeer
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class RepositoryImplTest {
    private val remote = mockk<Remote>()
    private val local = mockk<Local>()
    private val beerMapper = mockk<DataBeerMapper>()
    private val repository = RepositoryImpl(remote, local, beerMapper)

    @Test
    fun `given LocalBeers, when getBeers, then return data`() = runBlocking {
        val localBeers = listOf(makeLocalBeer())
        val beers = listOf(makeBeer())

        coEvery { local.getBeers() } returns flow { emit(localBeers) }
        every { with(beerMapper) { localBeers.localToDomain() } } returns beers

        val flow = repository.getBeers()

        flow.collect {repoBeers ->
            localBeers.zip(repoBeers).map {
                assertEquals(it.first.id, it.second.id)
                assertEquals(it.first.name, it.second.name)
                assertEquals(it.first.image, it.second.image)
                assertEquals(it.first.abv, it.second.abv)
                assertEquals(it.first.ibu, it.second.ibu)
                assertEquals(it.first.tagLine, it.second.tagLine)
            }
            assertEquals(repoBeers.size, localBeers.size)
        }
    }

    @Test
    fun `given Empty LocalBeers, when getBeers, then return data`() = runBlocking {
        val localBeers = listOf(makeLocalBeer())
        val remoteBeers = listOf(makeRemoteBeer())
        val beers = listOf(makeBeer())

        coEvery { local.getBeers() } returns flow { emit(emptyList()) }
        coEvery { remote.getBeers() } returns remoteBeers
        coEvery { local.storeBeers(localBeers) } returns Unit

        every { with(beerMapper) { remoteBeers.toLocal() } } returns localBeers
        every { with(beerMapper) { remoteBeers.remoteToDomain() } } returns beers

        val flow = repository.getBeers()

        flow.collect {repoBeers ->
            remoteBeers.zip(repoBeers).map {
                assertEquals(it.first.id, it.second.id)
                assertEquals(it.first.name, it.second.name)
                assertEquals(it.first.image_url, it.second.image)
                assertEquals(it.first.abv.toString(), it.second.abv)
                assertEquals(it.first.ibu.toString(), it.second.ibu)
                assertEquals(it.first.tagline, it.second.tagLine)
            }
            assertEquals(repoBeers.size, remoteBeers.size)
        }
    }

    @Test
    fun `given LocalBeer, when getBeer, then return data`() = runBlocking {
        val localBeer = makeLocalBeer()
        val beer = makeBeer()

        coEvery { local.getBeer("id") } returns flow { emit(localBeer) }
        every { with(beerMapper) { localBeer.localToDomain() } } returns beer

        val flow = repository.getBeer("id")

        flow.collect {repoBeers ->
            assertEquals(1, repoBeers.id)
            assertEquals("name", repoBeers.name)
            assertEquals("image", repoBeers.image)
            assertEquals("1.0", repoBeers.abv)
            assertEquals("1.0", repoBeers.ibu)
            assertEquals("tagLine", repoBeers.tagLine)
        }
    }
}