package com.wanggle.catchnet.view.activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter

import com.wanggle.catchnet.R
import com.wanggle.catchnet.view.base.BaseActivity
import com.wanggle.catchnet.view.fragment.CatchNetListFragment
import kotlinx.android.synthetic.main.activity_catch_net.*

class CatchNetActivity : BaseActivity() {
    private val fragments: ArrayList<Fragment> = ArrayList()
    private var titleArray: Array<String> = arrayOf("成功", "失败")
    private lateinit var successFragment: CatchNetListFragment
    private lateinit var failureFragment: CatchNetListFragment

    override fun initData() {
        successFragment = CatchNetListFragment.newInstance(CatchNetListFragment.success)
        fragments.add(successFragment)
        failureFragment = CatchNetListFragment.newInstance(CatchNetListFragment.failure)
        fragments.add(failureFragment)

        tl_request?.setupWithViewPager(vp_request, true)

        val pagerAdapter: FragmentPagerAdapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(p0: Int): Fragment {
                return fragments[p0]
            }

            override fun getCount(): Int {
                return titleArray.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return titleArray[position]
            }

        }
        vp_request?.adapter = pagerAdapter
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_catch_net
    }
}