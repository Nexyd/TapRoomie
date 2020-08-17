package dani.kotlin.data

import com.google.gson.annotations.SerializedName

data class BeerInfo(
    val id: String?,
    val name: String?,
    val tagline: String?,
    val description: String?,
    @SerializedName("image_url") val image: String?,
    @SerializedName("abv") val alcoholByVolume: String?,
    @SerializedName("ibu") val bitterness: String?,
    @SerializedName("food_pairing") val foodPairing: List<String>?,
    private var _availability: Boolean? = true
) {
    var availability
        get() = _availability ?: true
        set(value) { _availability = value }
}