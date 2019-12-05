package com.indramahkota.footballmatchschedule.ui.main.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.indramahkota.footballmatchschedule.R
import com.indramahkota.footballmatchschedule.data.source.remote.apimodel.LeagueApiModel
import kotlinx.android.extensions.LayoutContainer
import org.jetbrains.anko.AnkoContext

class LeagueAdapter (
    private val items: List<LeagueApiModel>, private val listener: (LeagueApiModel) -> Unit
) : RecyclerView.Adapter<LeagueAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = ViewHolder( ItemsLeague().createView(AnkoContext.Companion.create(parent.context, parent)))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    inner class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private val image = itemView.findViewById<ImageView>(ItemsLeague.clubImage)
        private val name = itemView.findViewById<TextView>(ItemsLeague.clubName)

        fun bind(item: LeagueApiModel, listener: (LeagueApiModel) -> Unit) {
            Glide.with(itemView.context)
                .load(item.imgLeague)
                .placeholder(R.drawable.spinner_animation)
                .error(R.drawable.image_error)
                .into(image)
            name.text = item.strLeague
            itemView.setOnClickListener { listener(item) }
        }
    }
}