package dani.kotlin

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dani.kotlin.data.db.Beer
import dani.kotlin.data.db.BeerDao
import dani.kotlin.data.db.BeerDatabase
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class BeerDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var beerDao: BeerDao
    private lateinit var db: BeerDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()

        db = Room.inMemoryDatabaseBuilder(
            context, BeerDatabase::class.java)
            .allowMainThreadQueries().build()

        beerDao = db.beerDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetBeer() {
        val beer = Beer(0, "name", "beer", "tagline",
            "desc", "image_url","alcohol",
            "bitterness", "food_pairing", false)

        beerDao.insert(beer)
        val allBeers = beerDao.loadByAvailability(false)
        assertEquals(allBeers[0].availability, beer.availability)
    }

    @Test
    @Throws(Exception::class)
    fun getAllBeers() {
        val beer = Beer(1, "name1", "beer1", "tagline1",
            "desc1", "image_url1","alcohol1",
            "bitterness1", "food_pairing1", false)

        val beer2 = Beer(2, "name2", "beer2", "tagline2",
            "desc2", "image_url2","alcohol2",
            "bitterness2", "food_pairing2", false)

        beerDao.insert(beer)
        beerDao.insert(beer2)

        val allBeers = beerDao.loadByAvailability(false)
        assertEquals(allBeers[0].availability, beer .availability)
        assertEquals(allBeers[1].availability, beer2.availability)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAll() {
        val beer = Beer(3, "name3", "beer3", "tagline3",
            "desc3", "image_url3","alcohol3",
            "bitterness3", "food_pairing#", false)
        val beer2 = Beer(4, "name4", "beer4", "tagline4",
            "desc4", "image_url4","alcohol4",
            "bitterness4", "food_pairing4", false)

        beerDao.insert(beer)
        beerDao.insert(beer2)
        beerDao.deleteAll()

        val allBeers = beerDao.loadByAvailability(false)
        Assert.assertTrue(allBeers.isEmpty())
    }
}