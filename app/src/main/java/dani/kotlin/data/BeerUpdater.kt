package dani.kotlin.data

interface BeerUpdater {
    fun onUpdateReceived(beers: ArrayList<BeerInfo>)
}