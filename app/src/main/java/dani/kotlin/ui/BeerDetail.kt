package dani.kotlin.ui

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import dani.kotlin.R
import dani.kotlin.data.DetailViewModel
import dani.kotlin.databinding.BeerDetailBinding

class BeerDetail : AppCompatActivity() {
    private lateinit var binding: BeerDetailBinding
    private val data = DetailViewModel.info
    private var available: Boolean? = null
    private var position  = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = BeerDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent != null) {
            position = intent.getIntExtra(
                "position", 0)
        }

        binding.requestReplacement.setOnClickListener {
            available = false
            binding.notAvailable.visibility = View.VISIBLE
            binding.requestReplacement.isEnabled = false
        }

        data.observe(this, Observer {
            Picasso.get().load(it.image).into(binding.detailImage)
            binding.detailName.text = formatText(R.string.detail_name, it.name)
            binding.detailDescription.text = formatText(R.string.detail_description, it.description)
            binding.detailAlcohol.text = formatText(R.string.detail_alcohol, it.alcoholByVolume)
            binding.detailBitterness.text  = formatText(R.string.detail_bitterness, it.bitterness)
            binding.detailFoodPairing.text = getString(R.string.detail_food_pairing,
                it.foodPairing?.get(0), it.foodPairing?.get(1), it.foodPairing?.get(2))

            if (!it.availability) {
                binding.notAvailable.visibility = View.VISIBLE
                binding.requestReplacement.isEnabled = false
            }
        })
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("position",  position)

        if (available != null)
            intent.putExtra("available", available!!)

        setResult(Activity.RESULT_OK, intent)

        super.onBackPressed()
    }

    private fun formatText(res: Int, text: String?): Spanned {
        return if (Build.VERSION.SDK_INT >= 24)
            Html.fromHtml(getString(res, text),
                HtmlCompat.FROM_HTML_MODE_LEGACY)
        else
            Html.fromHtml(getString(res, text))
    }
}