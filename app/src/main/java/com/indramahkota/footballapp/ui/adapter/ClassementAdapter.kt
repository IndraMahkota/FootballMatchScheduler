package com.indramahkota.footballapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.indramahkota.footballapp.R
import com.indramahkota.footballapp.data.source.remote.apimodel.ClassementApiModel
import kotlinx.android.synthetic.main.item_classement.view.*

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
                pld.text = classement.played
                win.text = classement.win
                draw.text = classement.draw
                loss.text = classement.loss
                gf.text = classement.goalsfor
                ga.text = classement.goalsagainst

                Glide.with(this)
                    .load(classement.image)
                    .placeholder(R.drawable.spinner_animation)
                    .error(R.drawable.image_error)
                    .transform(RoundedCorners(8))
                    .into(image_team)
            }
            itemView.setOnClickListener { listener(classement) }
        }
    }
}