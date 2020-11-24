package com.wanggle.catchnet.view.adapter
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.wanggle.catchnet.R
import com.wanggle.catchnet.provider.DBOpenHelper
import com.wanggle.catchnet.view.activity.CatchNetDetailActivity
import com.wanggle.catchnet.view.model.CatchNetRequestBean
import com.wanggle.catchnet.view.viewholder.CatchNetByTimeViewHolder

class CatchNetByTimeAdapter(context: Context?, requestBeans: ArrayList<CatchNetRequestBean>) : RecyclerView.Adapter<CatchNetByTimeViewHolder>() {
    private val currentContext = context
    private val requestList = requestBeans
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CatchNetByTimeViewHolder {
        val itemView = layoutInflater.inflate(R.layout.item_catch_net_by_time, p0, false)
        return CatchNetByTimeViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return requestList.size
    }

    override fun onBindViewHolder(p0: CatchNetByTimeViewHolder, p1: Int) {
        p0.tvRequestName.text = requestList[p1].requestName
        p0.tvRequestTime.text = requestList[p1].requestTime
        p0.clRequest.setOnClickListener {
            val intent = Intent(currentContext, CatchNetDetailActivity().javaClass)
            intent.putExtra(DBOpenHelper.id, requestList[p1].id)
            currentContext?.startActivity(intent)
        }
    }
}