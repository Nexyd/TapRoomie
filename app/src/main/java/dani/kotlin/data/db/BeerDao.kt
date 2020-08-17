package dani.kotlin.data.db

import androidx.room.*

@Dao
interface BeerDao {
    @Query("SELECT * FROM beer WHERE availability LIKE (:available)")
    fun loadByAvailability(available: Boolean): List<Beer>

    @Query("SELECT COUNT(*) FROM beer WHERE beerId LIKE (:id)")
    fun exists(id: String): Int?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(beer: Beer)

    @Update suspend fun update(beer: Beer)

    @Query("DELETE FROM beer")
    fun deleteAll()
}