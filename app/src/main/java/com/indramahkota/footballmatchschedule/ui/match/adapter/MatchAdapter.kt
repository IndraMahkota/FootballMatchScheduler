package com.indramahkota.footballmatchschedule.ui.match.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.indramahkota.footballmatchschedule.R
import com.indramahkota.footballmatchschedule.data.source.remote.apimodel.MatchDetailsApiModel
import com.indramahkota.footballmatchschedule.utilities.formatDateFromString
import kotlinx.android.synthetic.main.item_match_tab.view.*

class MatchAdapter(private val matchList: List<MatchDetailsApiModel>?,
                   private val listener: (MatchDetailsApiModel?) -> Unit
) : RecyclerView.Adapter<MatchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate( R.layout.item_match_tab, parent, false )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int =  matchList?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(matchList?.get(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(match: MatchDetailsApiModel?) {
            with(itemView) {
                tvDate.text = formatDateFromString(match?.dateEvent ?: "")
                tvTeam1.text = match?.strHomeTeam ?: ""
                tvTeam2.text = match?.strAwayTeam ?: ""
                tvSkorTeam1.text = match?.intHomeScore ?: "-"
                tvSkorTeam2.text = match?.intAwayScore ?: "-"
            }
            itemView.setOnClickListener { listener(match) }
        }
    }
}