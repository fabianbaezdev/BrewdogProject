package cl.neoxcore.brewdogproject.data.remote.model

data class RemoteMethod(
    val fermentation: RemoteFermentation,
    val mash_temp: List<RemoteMashTemp>,
    val twist: Any
)