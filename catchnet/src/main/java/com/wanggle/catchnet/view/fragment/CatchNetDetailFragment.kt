package com.wanggle.catchnet.view.fragment
import android.os.Bundle
import android.widget.TextView
import com.wanggle.catchnet.R
import com.wanggle.catchnet.view.base.BaseFragment

class CatchNetDetailFragment : BaseFragment() {
    private var tvCatchNetDetail: TextView? = null

    private var detail: String? = null

    companion object {
        fun newInstance(detail: String?): CatchNetDetailFragment {
            val args = Bundle()
            args.putString("detail", detail)
            val fragment = CatchNetDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initData() {
        tvCatchNetDetail = view.findViewById(R.id.tv_catch_net_detail)

        detail = arguments?.getString("detail")
        tvCatchNetDetail?.text = detail
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_catch_net_detail
    }
}