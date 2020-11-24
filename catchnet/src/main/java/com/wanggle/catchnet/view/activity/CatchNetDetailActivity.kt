package com.wanggle.catchnet.view.activity
import android.net.Uri
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.widget.TextView
import com.wanggle.catchnet.R
import com.wanggle.catchnet.provider.DBOpenHelper
import com.wanggle.catchnet.view.base.BaseActivity
import com.wanggle.catchnet.view.fragment.CatchNetDetailFragment
import com.wanggle.catchnet.view.model.CatchNetRequestBean

class CatchNetDetailActivity : BaseActivity() {
    private var tvRequestUrl: TextView? = null
    private var tlRequestTitle: TabLayout? = null
    private var vpRequest: ViewPager? = null

    private var id = -1

    private var titleArray: Array<String>? = null

    private val fragments: ArrayList<Fragment> = ArrayList()

    override fun initData() {
        id = intent.getIntExtra(DBOpenHelper.id, -1)

        tvRequestUrl = findViewById(R.id.tv_request_url)
        tlRequestTitle = findViewById(R.id.tl_request_title)
        vpRequest = findViewById(R.id.vp_request)

        val requestUri = Uri.parse("com.wanggle.catchnet.provider.CustomContentProvider/" + DBOpenHelper.requestTable)
        val requestCursor = contentResolver.query(requestUri, arrayOf(DBOpenHelper.id, DBOpenHelper.request, DBOpenHelper.response, DBOpenHelper.interfaceName, DBOpenHelper.header, DBOpenHelper.requestTime, DBOpenHelper.url, DBOpenHelper.requestMethod),
                "_id = '$id'", null, null)
        val requestBean = CatchNetRequestBean()
        if (requestCursor != null) {
            if (requestCursor.moveToFirst()) {
                requestBean.request = requestCursor.getString(requestCursor.getColumnIndex(
                    DBOpenHelper.request))
                requestBean.response = requestCursor.getString(requestCursor.getColumnIndex(
                    DBOpenHelper.response))
                requestBean.header = requestCursor.getString(requestCursor.getColumnIndex(
                    DBOpenHelper.header))
                requestBean.url = requestCursor.getString(requestCursor.getColumnIndex(DBOpenHelper.url))
                requestBean.requestMethod = requestCursor.getString(requestCursor.getColumnIndex(
                    DBOpenHelper.requestMethod))
            }
            requestCursor.close()
        }

        tvRequestUrl?.text = requestBean.requestMethod + " : " + requestBean.url

        if (!TextUtils.isEmpty(requestBean.request)) {
            titleArray = arrayOf("RequestBody", "Headers", "Response")
            val requestFragment = CatchNetDetailFragment.newInstance(requestBean.request)
            fragments.add(requestFragment)
        } else{
            titleArray = arrayOf("Headers", "Response")
        }

        val headerFragment = CatchNetDetailFragment.newInstance(requestBean.header)
        fragments.add(headerFragment)
        val responseFragment = CatchNetDetailFragment.newInstance(requestBean.response)
        fragments.add(responseFragment)

        tlRequestTitle?.setupWithViewPager(vpRequest, true)

        val pagerAdapter: FragmentPagerAdapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(p0: Int): Fragment {
                return fragments[p0]
            }

            override fun getCount(): Int {
                return titleArray!!.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return titleArray!![position]
            }

        }
        vpRequest?.adapter = pagerAdapter
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_catch_net_detail
    }
}