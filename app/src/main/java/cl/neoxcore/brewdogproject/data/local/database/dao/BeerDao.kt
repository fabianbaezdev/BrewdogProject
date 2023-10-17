package cl.neoxcore.brewdogproject.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import cl.neoxcore.brewdogproject.data.local.model.BEER_TABLE
import cl.neoxcore.brewdogproject.data.local.model.LocalBeer

@Dao
interface BeerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(localBeers: List<LocalBeer>)

    @Transaction
    @Query("SELECT * FROM $BEER_TABLE")
    suspend fun getAll(): List<LocalBeer>

    @Transaction
    @Query("SELECT * FROM $BEER_TABLE WHERE id=:id")
    suspend fun getById(id: String): LocalBeer

}
