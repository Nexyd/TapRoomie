package dani.kotlin.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dani.kotlin.adapter.BeerAdapter
import dani.kotlin.data.model.BeerInfo
import dani.kotlin.data.model.BeerViewModel
import dani.kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var beerAdapter: BeerAdapter
    private lateinit var model: BeerViewModel
    private val beers = ArrayList<BeerInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this)
            .get(BeerViewModel::class.java)

        model.init(this)
        binding.viewModel = model
        binding.lifecycleOwner = this
        beerAdapter = BeerAdapter(beers)
            //BeerViewModel.beers.value!!)

        binding.beerList.apply {
            layoutManager = LinearLayoutManager(baseContext)
            adapter = beerAdapter
        }

        BeerViewModel.beers.observe(this, Observer {
            this.beers.addAll(BeerViewModel.beers.value!!)
            runOnUiThread { beerAdapter.notifyDataSetChanged() }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val position: Int
        val availableTag = "available"
        var beer: BeerInfo? = null

        if (data != null) {
            position = data.getIntExtra(
                "position", 0)

            beer = BeerViewModel.beers.value?.get(position)
            if (data.hasExtra(availableTag))
                beer?.availability = data.getBooleanExtra(
                    availableTag, true)
        }

        runOnUiThread {
            beerAdapter.notifyDataSetChanged()
        }

        if (beer != null)
            model.persistData(beer)
    }
}