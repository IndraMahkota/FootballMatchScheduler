package com.indramahkota.footballmatchschedule.ui.fragment.match.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.indramahkota.footballmatchschedule.R
import com.indramahkota.footballmatchschedule.data.source.locale.entity.MatchEntity
import com.indramahkota.footballmatchschedule.utilities.Utilities.formatDateFromString
import kotlinx.android.synthetic.main.item_match_tab.view.*

class MatchAdapter(private val matchList: MutableList<MatchEntity>,
                   private val listener: (MatchEntity) -> Unit
) : RecyclerView.Adapter<MatchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate( R.layout.item_match_tab, parent, false )
        return ViewHolder(view)
    }

    private fun add(response: MatchEntity) {
        matchList.add(response)
        notifyItemInserted(matchList.size - 1)
    }

    fun addAll(postItems: List<MatchEntity>) {
        for (response in postItems) {
            add(response)
        }
    }

    private fun remove(postItems: MatchEntity) {
        val position: Int = matchList.indexOf(postItems)
        if (position > -1) {
            matchList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        while (itemCount > 0) {
            remove(getItem())
        }
    }

    private fun getItem(): MatchEntity {
        return matchList[0]
    }

    override fun getItemCount(): Int =  matchList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(matchList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(match: MatchEntity) {
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