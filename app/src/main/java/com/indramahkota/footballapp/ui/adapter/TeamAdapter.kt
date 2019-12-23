package com.indramahkota.footballapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.indramahkota.footballapp.R
import com.indramahkota.footballapp.data.source.locale.entity.TeamEntity
import kotlinx.android.synthetic.main.item_team.view.*

class TeamAdapter(private val teamList: MutableList<TeamEntity>,
                  private val listener: (TeamEntity) -> Unit
) : RecyclerView.Adapter<TeamAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate( R.layout.item_team, parent, false )
        return ViewHolder(view)
    }

    fun replace(items: List<TeamEntity>){
        teamList.clear()
        teamList.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int =  teamList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(teamList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(team: TeamEntity) {
            with(itemView) {
                strTeam.text = team.strTeam
                strDescription.text = team.strDescription

                Glide.with(this)
                    .load(team.strTeamBadge)
                    .placeholder(R.drawable.spinner_animation)
                    .error(R.drawable.image_error)
                    .transform(RoundedCorners(8))
                    .into(image_team)
            }
            itemView.setOnClickListener { listener(team) }
        }
    }
}