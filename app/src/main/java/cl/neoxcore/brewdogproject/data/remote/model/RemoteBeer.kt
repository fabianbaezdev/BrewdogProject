package cl.neoxcore.brewdogproject.data.remote.model

data class RemoteBeer(
    var abv: Double?,
    var brewers_tips: String?,
    var description: String?,
    var first_brewed: String?,
    var food_pairing: List<String>?,
    var ibu: Double?,
    var id: Int,
    var image_url: String?,
    var name: String?,
    var ph: Double?,
    var tagline: String?
)