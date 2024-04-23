package cl.neoxcore.brewdogproject.factory

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
}