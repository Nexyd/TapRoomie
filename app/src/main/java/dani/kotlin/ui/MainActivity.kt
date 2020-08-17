package dani.kotlin.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dani.kotlin.adapter.BeerAdapter
import dani.kotlin.data.BeerInfo
import dani.kotlin.data.BeerLoader
import dani.kotlin.data.BeerUpdater
import dani.kotlin.data.DetailViewModel
import dani.kotlin.data.db.Beer
import dani.kotlin.data.db.BeerDao
import dani.kotlin.data.db.BeerDatabase
import dani.kotlin.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), BeerUpdater {
    private var beers: ArrayList<BeerInfo> = ArrayList()
    private lateinit var binding: ActivityMainBinding
    private lateinit var beerAdapter: BeerAdapter
    private lateinit var db: BeerDatabase
    private lateinit var beerDao: BeerDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        initDatabase(baseContext)
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

            println("### size: ${unavailableBeers.size} ###")
        }

        Thread(runnable).start()
    }

    private fun initDatabase(context: Context) {
        val runnable = Runnable {
            db = BeerDatabase.getDatabase(context)
            beerDao = db.beerDao()
        }

        Thread(runnable).start()
    }

    override fun onUpdateReceived(beers: ArrayList<BeerInfo>) {
        runOnUiThread {
            this.beers.addAll(beers)
            loadUnavailableBeers()
            beerAdapter.notifyDataSetChanged()
        }
    }

    override fun onUpdateFailed() {
        // TODO: print a message "Error trying to load the items" to the screen
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var position = 0
        if (data != null) {
            position = data.getIntExtra(
                "position", 0)

            if (data.hasExtra("available"))
                beers[position].availability = data.getBooleanExtra(
                    "available", true)
        }

        runOnUiThread {
            beerAdapter.notifyDataSetChanged()
        }

        CoroutineScope(Dispatchers.IO).launch {
            val exists = beerDao.exists(beers[position].id!!)
            if (exists != null && exists > 0) {
                beerDao.update(Beer(beers[position]))
                println("### updated id: ${beers[position].id} ###")
            } else {
                beerDao.insert(Beer(beers[position]))
                println("### created id: ${beers[position].id} ###")
            }
        }
    }
}