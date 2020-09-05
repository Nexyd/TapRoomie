package dani.kotlin.data.model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dani.kotlin.data.BeerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BeerViewModel: ViewModel() {

    private lateinit var repository: BeerRepository

    companion object {
        var beers = MutableLiveData<ArrayList<BeerInfo>>()
        var detailBeer = MutableLiveData<BeerInfo>()
    }

    fun init(context: Context) {
        repository = BeerRepository()
        initDatabase(context)
        loadBeerList()
    }

    fun persistData(beer: BeerInfo) {
        repository.persistData(beer)
    }

    private fun initDatabase(context: Context) {
        repository.initDatabase(context)
    }

    private fun loadBeerList() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getData()
        }
    }
}