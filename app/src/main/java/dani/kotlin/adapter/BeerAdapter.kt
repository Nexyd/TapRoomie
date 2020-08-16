package dani.kotlin.adapter

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dani.kotlin.R
import dani.kotlin.data.BeerInfo
import dani.kotlin.data.DetailViewModel
import dani.kotlin.ui.BeerDetail

class BeerAdapter(
    private val dataSet: List<BeerInfo>)
    : RecyclerView.Adapter<BeerAdapter.BeerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,
        viewType: Int): BeerViewHolder
    {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.beer_card, parent,
            false) as CardView

        return BeerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        Picasso.get().load(dataSet[position].image).into(holder.beerImage)
        holder.beerName.text = dataSet[position].name
        holder.beerTag .text = dataSet[position].tagline
        holder.available = dataSet[position].availability

        holder.itemView.setOnClickListener {
            DetailViewModel.info.postValue(dataSet[position])
            val intent = Intent(it.context,
                BeerDetail::class.java)

            intent.putExtra("position", position)
            (it.context as Activity).startActivityForResult(
                intent, 23)
        }

        if (holder.available)
            setBackgroundWhite(holder.itemView)
        else
            setBackgroundGray (holder.itemView)
    }

    override fun getItemCount() = dataSet.size

    private fun setBackgroundGray(view: View) {
        val res = view.context.resources
        if (Build.VERSION.SDK_INT > 23)
            view.setBackgroundColor(res.getColor(
                R.color.disabled, null))
        else
            view.setBackgroundColor(
                res.getColor(R.color.disabled))
    }

    private fun setBackgroundWhite(view: View) {
        val res = view.context.resources
        if (Build.VERSION.SDK_INT > 23)
            view.setBackgroundColor(res.getColor(
                android.R.color.white, null))
        else
            view.setBackgroundColor(res.getColor(
                android.R.color.white))
    }

    class BeerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val beerImage = view.findViewById(R.id.beerImage)   as ImageView
        var beerName  = view.findViewById(R.id.beerName)    as TextView
        var beerTag   = view.findViewById(R.id.beerTagline) as TextView
        var available = true
    }
}