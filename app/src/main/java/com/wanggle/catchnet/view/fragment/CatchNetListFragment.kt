package com.wanggle.catchnet.view.fragment
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.wanggle.catchnet.R
import com.wanggle.catchnet.interceptor.RequestInterceptor
import com.wanggle.catchnet.provider.DBOpenHelper
import com.wanggle.catchnet.view.adapter.CatchNetByTimeAdapter
import com.wanggle.catchnet.view.adapter.CatchNetByTypeAdapter
import com.wanggle.catchnet.view.base.BaseFragment
import com.wanggle.catchnet.view.model.CatchNetRequestBean
import kotlinx.android.synthetic.main.fragment_catch_net_list.*

class CatchNetListFragment : BaseFragment() {
    //按时间分类的列表数据
    private var requestList: ArrayList<CatchNetRequestBean> = ArrayList()

    //按类别分类的列表数据
    private var groupList: ArrayList<String> = ArrayList()
    private var childRequestList:  ArrayList<ArrayList<CatchNetRequestBean>> = ArrayList()

    private lateinit var timeAdapter: CatchNetByTimeAdapter
    private lateinit var typeAdapter: CatchNetByTypeAdapter

    companion object {
        private const val responseKey = "responseKey"
        const val success = 0
        const val failure = 1

        fun newInstance(responseStatus: Int): CatchNetListFragment {
            val args = Bundle()
            args.putInt(responseKey, responseStatus)
            val fragment = CatchNetListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initData() {
        val responseStatus = arguments?.getInt(responseKey)
        val responseCode = RequestInterceptor.sCustomSuccessCode

        rv_catch_list?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val requestUri = Uri.parse("content://com.wanggle.catchnet.provider.CustomContentProvider/" + DBOpenHelper.requestTable)
        val requestCursor = context?.contentResolver
                ?.query(requestUri,
                        arrayOf(
                            DBOpenHelper.id,
                                DBOpenHelper.request,
                                DBOpenHelper.response,
                                DBOpenHelper.interfaceName,
                                DBOpenHelper.header,
                                DBOpenHelper.requestTime,
                                DBOpenHelper.url,
                                DBOpenHelper.requestMethod,
                                DBOpenHelper.responseCode),
                        when (responseStatus) {
                            success -> "responseCode = '$responseCode'"
                            else -> "responseCode != '$responseCode'"
                        }, null, null)
        if (requestCursor != null) {
            while (requestCursor.moveToNext()) {
                val requestBean = CatchNetRequestBean()
                val interfaceName = requestCursor.getString(requestCursor.getColumnIndex(
                    DBOpenHelper.interfaceName))
                requestBean.requestName = interfaceName
                requestBean.requestTime = requestCursor.getString(requestCursor.getColumnIndex(
                    DBOpenHelper.requestTime))
                requestBean.id = requestCursor.getInt(requestCursor.getColumnIndex(DBOpenHelper.id))
                requestBean.request = requestCursor.getString(requestCursor.getColumnIndex(
                    DBOpenHelper.request))
                requestBean.response = requestCursor.getString(requestCursor.getColumnIndex(
                    DBOpenHelper.response))
                requestBean.header = requestCursor.getString(requestCursor.getColumnIndex(
                    DBOpenHelper.header))
                requestBean.url = requestCursor.getString(requestCursor.getColumnIndex(DBOpenHelper.url))
                requestBean.responseCode = requestCursor.getInt(requestCursor.getColumnIndex(
                    DBOpenHelper.responseCode))
                requestList.add(requestBean)

                if (!groupList.contains(interfaceName)) {
                    groupList.add(interfaceName)
                }
                groupList.reverse()
            }
            requestCursor.close()

            for (str in groupList) {
                val childRequest: ArrayList<CatchNetRequestBean> = ArrayList()
                for (catchNetRequestBean in requestList) {
                    if (str == catchNetRequestBean.requestName) {
                        childRequest.add(catchNetRequestBean)
                    }
                }
                childRequest.reverse()
                childRequestList.add(childRequest)
            }
        }

        requestList.reverse()
        timeAdapter = CatchNetByTimeAdapter(context, requestList)
        rv_catch_list?.adapter = timeAdapter

        typeAdapter = CatchNetByTypeAdapter(context, groupList)
        typeAdapter.setData(childRequestList)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_catch_net_list
    }

    fun changSort() {
        if (rv_catch_list.adapter is CatchNetByTypeAdapter) {
            rv_catch_list.adapter = timeAdapter
        } else {
            rv_catch_list.adapter = typeAdapter
        }
    }
}