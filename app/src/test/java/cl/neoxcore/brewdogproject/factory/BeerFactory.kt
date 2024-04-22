package cl.neoxcore.brewdogproject.factory

import cl.neoxcore.brewdogproject.data.local.model.LocalBeer
import cl.neoxcore.brewdogproject.data.remote.model.RemoteAmount
import cl.neoxcore.brewdogproject.data.remote.model.RemoteBeer
import cl.neoxcore.brewdogproject.data.remote.model.RemoteBoilVolume
import cl.neoxcore.brewdogproject.data.remote.model.RemoteFermentation
import cl.neoxcore.brewdogproject.data.remote.model.RemoteHop
import cl.neoxcore.brewdogproject.data.remote.model.RemoteIngredients
import cl.neoxcore.brewdogproject.data.remote.model.RemoteMalt
import cl.neoxcore.brewdogproject.data.remote.model.RemoteMashTemp
import cl.neoxcore.brewdogproject.data.remote.model.RemoteMethod
import cl.neoxcore.brewdogproject.data.remote.model.RemoteTemp
import cl.neoxcore.brewdogproject.data.remote.model.RemoteTempX
import cl.neoxcore.brewdogproject.data.remote.model.RemoteVolume
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
        attenuation_level = 1.0,
        boil_volume = RemoteBoilVolume(
            unit = "unit",
            value = 1
        ),
        brewers_tips = "brewers_tips",
        contributed_by = "contributed_by",
        description = "description",
        ebc = 1.0,
        first_brewed = "first_brewed",
        food_pairing = listOf("food", "pairing"),
        ibu = 1.0,
        id = 1,
        image_url = "image",
        ingredients = RemoteIngredients(
            hops = listOf(
                RemoteHop(
                    add = "add",
                    amount = RemoteAmount(unit = "unit", value = 1.0),
                    attribute = "attribute",
                    name = "name"
                )
            ),
            malt = listOf(
                RemoteMalt(
                    amount = RemoteAmount(unit = "unit", value = 1.0),
                    name = "name"
                )
            ),
            yeast = "yeast"
        ),
        method = RemoteMethod(
            fermentation = RemoteFermentation(
                temp = RemoteTemp(unit = "unit", value = 1.0)
            ),
            mash_temp = listOf(
                RemoteMashTemp(duration = 1, temp = RemoteTempX(unit = "unit", value = 1))
            ),
            twist = "1"
        ),
        name = "name",
        ph = 1.0,
        srm = 1.0,
        tagline = "tagLine",
        target_fg = 1.0,
        target_og = 1.0,
        volume = RemoteVolume(unit = "unit", value = 1)
    )
}