package dani.kotlin.data

import android.content.Context
import dani.kotlin.callbacks.BeerUpdater
import dani.kotlin.data.db.Beer
import dani.kotlin.data.db.BeerDao
import dani.kotlin.data.db.BeerDatabase
import dani.kotlin.data.model.BeerInfo
import dani.kotlin.data.model.BeerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BeerRepository: BeerUpdater {

    private lateinit var beerDao: BeerDao
    private lateinit var db: BeerDatabase
    private val beers  = ArrayList<BeerInfo>()
    private val client = BeerLoader(this)

    suspend fun getData() {
        withContext(Dispatchers.IO) {
            client.loadBeerPages(2)
//            client.callApi(1)
//            client.callApi(2)
        }
    }

    fun initDatabase(context: Context) {
        val runnable = Runnable {
            db = BeerDatabase.getDatabase(context)
            beerDao = db.beerDao()
        }

        Thread(runnable).start()
    }

    fun persistData(beer: BeerInfo) {
        CoroutineScope(Dispatchers.IO).launch {
            val exists = beerDao.exists(beer.id!!)
            if (exists != null && exists > 0) {
                beerDao.update(Beer(beer))
            } else {
                beerDao.insert(Beer(beer))
            }
        }
    }

    override fun onUpdateReceived(beers: ArrayList<BeerInfo>) {
        this.beers.addAll(beers)
        loadUnavailableBeers()
        BeerViewModel.beers.postValue(beers)
    }

    private fun loadUnavailableBeers() {
        val runnable = Runnable {
            val unavailableBeers = beerDao.
                loadByAvailability(false)

            for (i in unavailableBeers.indices) {
                val items = beers.filter {
                    it.id == unavailableBeers[i].beerId
                }

                if (items.isNotEmpty())
                    items[0].availability = unavailableBeers[0].availability!!
            }
        }

        Thread(runnable).start()
    }
}