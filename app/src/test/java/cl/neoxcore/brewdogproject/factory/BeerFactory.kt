package cl.neoxcore.brewdogproject.factory

import cl.neoxcore.brewdogproject.data.local.model.LocalBeer
import cl.neoxcore.brewdogproject.data.remote.model.RemoteBeer
import cl.neoxcore.brewdogproject.domain.model.Beer

object BeerFactory {

    fun makeBeer() = Beer(
        id = 1,
        name = "name",
        image = "image",
        abv = "1.0",
        ibu = "1.0",
        tagLine = "tagLine"
    )

    fun makeLocalBeer() = LocalBeer(
        id = 1,
        name = "name",
        image = "image",
        abv = "1.0",
        ibu = "1.0",
        tagLine = "tagLine"
    )

    fun makeRemoteBeer() = RemoteBeer(
        abv = 1.0,
        brewers_tips = "brewers_tips",
        description = "description",
        first_brewed = "first_brewed",
        food_pairing = listOf("food", "pairing"),
        ibu = 1.0,
        id = 1,
        image_url = "image",
        name = "name",
        ph = 1.0,
        tagline = "tagLine"
    )
}