package dani.kotlin.data.db

//import androidx.lifecycle.LiveData
//
//// Declares the DAO as a private property in the constructor. Pass in the DAO
//// instead of the whole database, because you only need access to the DAO
//class BeerRepository(private val beerDao: BeerDao) {
//
//    // Room executes all queries on a separate thread.
//    // Observed LiveData will notify the observer when the data has changed.
//    val allBeers: LiveData<List<Beer>> = beerDao.getAll()
//
//    suspend fun insert(beer: Beer) {
//        beerDao.insert(beer)
//    }
//}