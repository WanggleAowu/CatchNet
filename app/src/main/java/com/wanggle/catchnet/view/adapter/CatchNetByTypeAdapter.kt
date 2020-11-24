package com.wanggle.catchnet.view.adapter
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import com.wanggle.catchnet.R
import com.wanggle.catchnet.view.model.CatchNetRequestBean
import com.wanggle.catchnet.view.viewholder.CatchNetByTypeViewHolder

class CatchNetByTypeAdapter(context: Context?, groups: ArrayList<String>) : RecyclerView.Adapter<CatchNetByTypeViewHolder>() {
    private val currentContext = context
    private val groupList = groups
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private lateinit var childRequestList: ArrayList<ArrayList<CatchNetRequestBean>>

    fun setData(childRequestList: ArrayList<ArrayList<CatchNetRequestBean>>) {
        this.childRequestList = childRequestList
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CatchNetByTypeViewHolder {
        val itemView: View = layoutInflater.inflate(R.layout.item_catch_net_by_type, p0, false)
        return CatchNetByTypeViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    override fun onBindViewHolder(p0: CatchNetByTypeViewHolder, p1: Int) {
        p0.tvTypeTitle.text = groupList[p1]
        val adapter = CatchNetByTimeAdapter(currentContext, childRequestList[p1])
        p0.rvTypeList.layoutManager = LinearLayoutManager(currentContext)
        p0.rvTypeList.adapter = adapter
        p0.tvTypeTitle.setOnClickListener {
            if (p0.rvTypeList.visibility == View.VISIBLE) {
                val hideAnim = TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 1.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f)
                hideAnim.duration = 500
                p0.rvTypeList.startAnimation(hideAnim)
                p0.rvTypeList.visibility = View.GONE
            } else{
                val showAnim = TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f)
                showAnim.duration = 500
                p0.rvTypeList.startAnimation(showAnim)
                p0.rvTypeList.visibility = View.VISIBLE
            }
        }
    }
}