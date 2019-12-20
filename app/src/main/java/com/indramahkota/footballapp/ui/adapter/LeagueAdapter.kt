package com.indramahkota.footballapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.indramahkota.footballapp.R
import com.indramahkota.footballapp.data.source.locale.entity.LeagueEntity
import kotlinx.android.synthetic.main.item_league.view.*

class LeagueAdapter(private val matchList: List<LeagueEntity>,
                   private val listener: (LeagueEntity) -> Unit
) : RecyclerView.Adapter<LeagueAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate( R.layout.item_league, parent, false )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int =  matchList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(matchList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(match: LeagueEntity) {
            with(itemView) {
                Glide.with(this)
                    .load(match.imgLeague)
                    .placeholder(R.drawable.spinner_animation)
                    .error(R.drawable.image_error)
                    .into(img_league)

                txt_league.text = match.strLeague
            }
            itemView.setOnClickListener { listener(match) }
        }
    }
}