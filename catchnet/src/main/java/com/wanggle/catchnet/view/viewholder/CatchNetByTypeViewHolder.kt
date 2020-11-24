package com.wanggle.catchnet.view.viewholder
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.wanggle.catchnet.R

class CatchNetByTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvTypeTitle: TextView = itemView.findViewById(R.id.tv_type_title)
    val rvTypeList: RecyclerView = itemView.findViewById(R.id.rv_type_list)
}