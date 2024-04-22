package cl.neoxcore.brewdogproject.data

import cl.neoxcore.brewdogproject.factory.BeerFactory.makeLocalBeer
import cl.neoxcore.brewdogproject.factory.BeerFactory.makeRemoteBeer
import org.junit.Assert.assertEquals
import org.junit.Test

class DataBeerMapperTest {
    private val mapper = DataBeerMapper()

    @Test
    fun `given RemoteBeers, when remoteToDomain, then Beers`() {
        val remoteBeers = listOf(makeRemoteBeer(), makeRemoteBeer())

        val beers = with(mapper) { remoteBeers.remoteToDomain() }

        remoteBeers.zip(beers).map {
            assertEquals(it.first.id, it.second.id)
            assertEquals(it.first.name, it.second.name)
            assertEquals(it.first.image_url, it.second.image)
            assertEquals(it.first.abv.toString(), it.second.abv)
            assertEquals(it.first.ibu.toString(), it.second.ibu)
            assertEquals(it.first.tagline, it.second.tagLine)
        }
        assertEquals(remoteBeers.size, beers.size)

    }

    @Test
    fun `given LocalBeer, when localToDomain, then Beer`() {
        val localBeer = makeLocalBeer()

        val beer = with(mapper) { localBeer.localToDomain() }

        assertEquals(1, beer.id)
        assertEquals("name", beer.name)
        assertEquals("image", beer.image)
        assertEquals("1.0", beer.abv)
        assertEquals("1.0", beer.ibu)
        assertEquals("tagLine", beer.tagLine)
    }

    @Test
    fun `given LocalBeers, when localToDomain, then Beers`() {
        val localBeers = listOf(makeLocalBeer(), makeLocalBeer())

        val beers = with(mapper) { localBeers.localToDomain() }

        localBeers.zip(beers).map {
            assertEquals(it.first.id, it.second.id)
            assertEquals(it.first.name, it.second.name)
            assertEquals(it.first.image, it.second.image)
            assertEquals(it.first.abv, it.second.abv)
            assertEquals(it.first.ibu, it.second.ibu)
            assertEquals(it.first.tagLine, it.second.tagLine)
        }
        assertEquals(localBeers.size, beers.size)
    }

    @Test
    fun `given RemoteBeers, when toLocal, then LocalBeers`() {
        val remoteBeers = listOf(makeRemoteBeer(), makeRemoteBeer())

        val localBeers = with(mapper) { remoteBeers.toLocal() }

        remoteBeers.zip(localBeers).map {
            assertEquals(it.first.id, it.second.id)
            assertEquals(it.first.name, it.second.name)
            assertEquals(it.first.image_url, it.second.image)
            assertEquals(it.first.abv.toString(), it.second.abv)
            assertEquals(it.first.ibu.toString(), it.second.ibu)
            assertEquals(it.first.tagline, it.second.tagLine)
        }
        assertEquals(remoteBeers.size, localBeers.size)

    }
}