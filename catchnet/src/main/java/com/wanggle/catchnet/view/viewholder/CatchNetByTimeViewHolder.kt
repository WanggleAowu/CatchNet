package com.wanggle.catchnet.view.viewholder
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.wanggle.catchnet.R

class CatchNetByTimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val clRequest: ConstraintLayout = itemView.findViewById(R.id.cl_request)
    val tvRequestName: TextView = itemView.findViewById(R.id.tv_request_name)
    val tvRequestTime: TextView = itemView.findViewById(R.id.tv_request_time)
}