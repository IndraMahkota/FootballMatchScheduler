package com.indramahkota.footballmatchschedule.ui.match.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.indramahkota.footballmatchschedule.R
import com.indramahkota.footballmatchschedule.data.source.remote.model.MatchModel
import com.indramahkota.footballmatchschedule.utilities.formatDateFromString
import kotlinx.android.synthetic.main.item_match_tab.view.*

class MatchAdapter(private val matchList: List<MatchModel>,
                   private val listener: (MatchModel) -> Unit
) : RecyclerView.Adapter<MatchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate( R.layout.item_match_tab, parent, false )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int =  matchList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(matchList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(match: MatchModel) {
            with(itemView) {
                tvDate.text = formatDateFromString(match.dateEvent)
                tvTeam1.text = match.strHomeTeam
                tvTeam2.text = match.strAwayTeam
                tvSkorTeam1.text = match.intHomeScore
                tvSkorTeam2.text = match.intAwayScore

                Glide.with(this)
                    .load(match.sourceHomeImage)
                    .placeholder(R.drawable.spinner_animation)
                    .error(R.drawable.image_error)
                    .transform(RoundedCorners(8))
                    .into(image_team1)

                Glide.with(this)
                    .load(match.sourceAwayImage)
                    .placeholder(R.drawable.spinner_animation)
                    .error(R.drawable.image_error)
                    .transform(RoundedCorners(8))
                    .into(image_team2)
            }
            itemView.setOnClickListener { listener(match) }
        }
    }
}