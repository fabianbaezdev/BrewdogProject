package cl.neoxcore.brewdogproject.data.remote.model

data class RemoteIngredients(
    val hops: List<RemoteHop>,
    val malt: List<RemoteMalt>,
    val yeast: String
)