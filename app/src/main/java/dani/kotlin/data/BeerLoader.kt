package dani.kotlin.data

import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import okhttp3.*
import java.io.IOException

class BeerLoader(private val listener: BeerUpdater) {
    private val client = OkHttpClient()
    private val gson = Gson()

    suspend fun getBeers() {
        withContext(Dispatchers.IO) {
            callApi(1)
            callApi(2)
        }
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
                    listener.onUpdateReceived(result
                        .toList() as ArrayList<BeerInfo>)
                } else {
                    listener.onUpdateFailed()
                }
            }
        })
    }
}