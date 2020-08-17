package dani.kotlin.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dani.kotlin.data.BeerInfo

@Entity
data class Beer(
    @PrimaryKey
    @ColumnInfo(name = "beerId")       val beerId: String,
    @ColumnInfo(name = "name")         val name: String?,
    @ColumnInfo(name = "tagline")      val tagline: String?,
    @ColumnInfo(name = "description")  val description: String?,
    @ColumnInfo(name = "image_url")    val imageURL: String?,
    @ColumnInfo(name = "alcohol")      val alcoholByVolume: String?,
    @ColumnInfo(name = "bitterness")   val bitterness: String?,
    @ColumnInfo(name = "food_pairing") val foodPairing:  String?,
    @ColumnInfo(name = "availability") val availability: Boolean?
) {
    constructor(beer: BeerInfo) : this(beer.id!!,
        beer.name, beer.tagline, beer.description,
        beer.image, beer.alcoholByVolume, beer.bitterness,
        beer.foodPairing.toString(), beer.availability)
}