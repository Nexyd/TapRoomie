package dani.kotlin.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dani.kotlin.R
import dani.kotlin.adapter.BeerAdapter
import dani.kotlin.data.BeerInfo
import dani.kotlin.data.BeerLoader
import dani.kotlin.data.BeerUpdater
import dani.kotlin.data.DetailViewModel
import dani.kotlin.databinding.ActivityMainBinding
import dani.kotlin.databinding.BeerDetailBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), BeerUpdater {
    private var beers: ArrayList<BeerInfo> = ArrayList()
    private lateinit var binding: ActivityMainBinding
    private lateinit var beerAdapter: BeerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        val listener = this

        CoroutineScope(Dispatchers.IO).launch {
            BeerLoader(listener).getBeers()
        }

        beerAdapter = BeerAdapter(beers)
        binding.beerList.apply {
            layoutManager = LinearLayoutManager(baseContext)
            adapter = beerAdapter
        }
    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this)
            .get(DetailViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    override fun onUpdateReceived(beers: ArrayList<BeerInfo>) {
        runOnUiThread {
            this.beers.addAll(beers)
            beerAdapter.notifyDataSetChanged()
        }
    }

    override fun onUpdateFailed() {
        // TODO: print a message "Error trying to load the items" to the screen
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            val position = data.getIntExtra(
                "position", 0)

            if (data.hasExtra("available"))
                beers[position].availability = data.getBooleanExtra(
                    "available", true)
        }

        beerAdapter.notifyDataSetChanged()
    }
}