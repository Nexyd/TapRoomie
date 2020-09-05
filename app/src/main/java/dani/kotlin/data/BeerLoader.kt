package dani.kotlin.data

import com.google.gson.Gson
import dani.kotlin.callbacks.BeerUpdater
import dani.kotlin.data.model.BeerInfo
import dani.kotlin.data.model.BeerViewModel
import okhttp3.*
import java.io.IOException

class BeerLoader(private val listener: BeerUpdater) {

    private val client = OkHttpClient()
    private val gson = Gson()
//    private val beers = ArrayList<BeerInfo>()

    fun loadBeerPages(numPages: Int) {
        for (page: Int in 1..numPages)
            callApi(page)

//        listener.onUpdateReceived(beers)
    }

    private fun callApi(page: Int) {
        val endpoint = "https://api.punkapi.com/v2/beers?page=$page&per_page=50"
        val request  = Request.Builder().url(endpoint).build()
        var json: String? = ""

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, exception: IOException) {
                println(exception.printStackTrace())
            }
            override fun onResponse(call: Call, response: Response) {
                json = response.body()?.string()
                val result = gson.fromJson(json,
                    Array<BeerInfo>::class.java)

                if (result != null) {
//                    beers.addAll(result.toList()
//                        as ArrayList<BeerInfo>)

                    listener.onUpdateReceived(result
                        .toList() as ArrayList<BeerInfo>)
                }
            }
        })
    }
}