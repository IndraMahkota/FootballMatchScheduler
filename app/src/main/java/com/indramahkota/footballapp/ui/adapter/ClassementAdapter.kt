package com.indramahkota.footballapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.indramahkota.footballapp.R
import com.indramahkota.footballapp.data.source.remote.apimodel.ClassementApiModel
import kotlinx.android.synthetic.main.item_team.view.*

class ClassementAdapter(private val classementList: MutableList<ClassementApiModel>,
                        private val listener: (ClassementApiModel) -> Unit
) : RecyclerView.Adapter<ClassementAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate( R.layout.item_classement, parent, false )
        return ViewHolder(view)
    }

    fun replace(items: List<ClassementApiModel>){
        classementList.clear()
        classementList.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int =  classementList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(classementList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(classement: ClassementApiModel) {
            with(itemView) {
                strTeam.text = classement.name
                strDescription.text = classement.played
            }
            itemView.setOnClickListener { listener(classement) }
        }
    }
}