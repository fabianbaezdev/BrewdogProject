package cl.neoxcore.brewdogproject.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = BEER_TABLE)
data class LocalBeer(
    @PrimaryKey
    val id: Int,
    val name: String,
    val image: String,
    val abv: String,
    val ibu: String,
    val tagLine: String
)

const val BEER_TABLE = "beer_table"
