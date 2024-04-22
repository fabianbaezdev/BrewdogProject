package cl.neoxcore.brewdogproject.data.remote

import cl.neoxcore.brewdogproject.data.remote.retrofit.Webservice
import cl.neoxcore.brewdogproject.factory.BeerFactory.makeRemoteBeer
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class RemoteImplTest {
    private val webService = mockk<Webservice>()
    private val remote = RemoteImpl(webService)

    @Test
    fun `given RemoteBeer, when getBeers, the return data`() = runBlocking {
        val remoteBeers = listOf(makeRemoteBeer())
        coEvery { webService.getBeers() } returns remoteBeers

        val result = remote.getBeers()

        assertEquals(remoteBeers, result)
    }
}