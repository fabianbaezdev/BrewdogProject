package cl.neoxcore.brewdogproject.data

import cl.neoxcore.brewdogproject.data.local.model.LocalBeer
import cl.neoxcore.brewdogproject.data.remote.model.RemoteBeer
import cl.neoxcore.brewdogproject.domain.model.Beer
import javax.inject.Inject

class DataBeerMapper @Inject constructor() {

    private fun RemoteBeer.remoteToDomain() =
        Beer(
            id = id,
            name = name,
            image = image_url,
            abv = abv.toString(),
            ibu = ibu.toString(),
            tagLine = tagline
        )

    fun List<RemoteBeer>.remoteToDomain() = this.map { it.remoteToDomain() }

    fun LocalBeer.localToDomain() =
        Beer(
            id = id,
            name = name,
            image = image,
            abv = abv,
            ibu = ibu,
            tagLine = tagLine
        )

    fun List<LocalBeer>.localToDomain() = this.map { it.localToDomain() }

    private fun RemoteBeer.toLocal() =
        LocalBeer(
            id = id,
            name = name,
            image = image_url,
            abv = abv.toString(),
            ibu = ibu.toString(),
            tagLine = tagline
        )

    fun List<RemoteBeer>.toLocal() = this.map { it.toLocal() }
}