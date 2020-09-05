package dani.kotlin.callbacks

import dani.kotlin.data.model.BeerInfo

interface BeerUpdater {
    fun onUpdateReceived(beers: ArrayList<BeerInfo>)
}