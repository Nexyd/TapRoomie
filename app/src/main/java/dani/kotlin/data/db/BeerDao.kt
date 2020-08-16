package dani.kotlin.data.db

//import androidx.lifecycle.LiveData
//import androidx.room.*
//
//@Dao
//interface BeerDao {
//    @Query("SELECT * FROM beer")
//    fun getAll(): LiveData<List<Beer>>
//
//    @Query("SELECT * FROM beer WHERE uid IN (:beerIds)")
//    fun loadAllByIds(beerIds: IntArray): List<Beer>
//
//    @Query("SELECT * FROM beer WHERE availability LIKE (:available)")
//    fun loadByAvailability(available: Boolean): List<Beer>
//
//    @Query("SELECT * FROM beer WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): Beer
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insert(beer: Beer)
//
//    @Insert fun insertAll(vararg beers: Beer)
//    @Delete fun delete(beer: Beer)
//}