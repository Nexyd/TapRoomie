package dani.kotlin.data.db

//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//
//class BeerViewModel(application: Application) : AndroidViewModel(application) {
//
//    private val repository: BeerRepository
//    // Using LiveData and caching what getAll returns has several benefits:
//    // - We can put an observer on the data (instead of polling for changes) and only update the
//    //   the UI when the data actually changes.
//    // - Repository is completely separated from the UI through the ViewModel.
//    val allBeers: LiveData<List<Beer>>
//
//    init {
//        val beersDao = BeerDatabase.getDatabase(application).beerDao()
//        repository = BeerRepository(beersDao)
//        allBeers = repository.allBeers
//    }
//
//    fun insert(beer: Beer) =
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.insert(beer)
//        }
//}