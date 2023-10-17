package cl.neoxcore.brewdogproject.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import cl.neoxcore.brewdogproject.data.local.database.dao.BeerDao
import cl.neoxcore.brewdogproject.data.local.model.LocalBeer

@Database(version = 1, entities = [LocalBeer::class], exportSchema = false)
abstract class DatabaseBuilder : RoomDatabase() {
    abstract fun beerDao(): BeerDao
}
