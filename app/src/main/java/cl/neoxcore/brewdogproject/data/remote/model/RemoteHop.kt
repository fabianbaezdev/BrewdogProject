package cl.neoxcore.brewdogproject.data.remote.model

data class RemoteHop(
    val add: String,
    val amount: RemoteAmount,
    val attribute: String,
    val name: String
)