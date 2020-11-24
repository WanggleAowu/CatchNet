package com.wanggle.catchnet.widget

import android.content.Context
import android.content.Intent
import android.support.v7.widget.AppCompatTextView
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import com.wanggle.catchnet.R
import com.wanggle.catchnet.utils.DensityUtil
import com.wanggle.catchnet.utils.ScreenUtils
import com.wanggle.catchnet.view.activity.CatchNetActivity

class CatchNetEnterView(context: Context) : AppCompatTextView(context), View.OnClickListener {
    companion object {
        var realX: Float = 0f
        var realY: Float = 0f
    }
    private var startX = 0f
    private var startY = 0f
    private var isClick = true

    init {
        initView()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.makeMeasureSpec(DensityUtil.dip2px(context, 40f), MeasureSpec.EXACTLY)
        val height = MeasureSpec.makeMeasureSpec(DensityUtil.dip2px(context, 40f), MeasureSpec.EXACTLY)
        super.onMeasure(width, height)
    }

    private fun initView() {
        text = "抓包"
        background = resources.getDrawable(R.drawable.red_corner, null)
        gravity = Gravity.CENTER
        setTextColor(resources.getColor(R.color.white))
        y = if (realY == 0f) (ScreenUtils.getScreenHeight(context) / 2).toFloat() else realY
        x = if (realX == 0f) ScreenUtils.getScreenWidth(context) - DensityUtil.dip2px(context, 40f).toFloat() else realX
        setOnClickListener(this)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.rawX
                startY = event.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                if (event.rawX - startX > 50 || event.rawY - startY > 50) {
                    isClick = false
                }

                y = when {
                    event.rawY < 0 -> 0f
                    event.rawY > ScreenUtils.getScreenHeight(context) - DensityUtil.dip2px(context, 40f) -> (ScreenUtils.getScreenHeight(context) - DensityUtil.dip2px(context, 40f).toFloat())
                    else -> event.rawY
                }
                realY = y
                x = when {
                    event.rawX < 0 -> 0f
                    event.rawX > ScreenUtils.getScreenWidth(context) - DensityUtil.dip2px(context, 40f) -> ScreenUtils.getScreenWidth(context) - DensityUtil.dip2px(context, 40f).toFloat()
                    else -> event.rawX
                }
                realX = x
            }
            MotionEvent.ACTION_UP -> {
                if (isClick) {
                    context.startActivity(Intent(context, CatchNetActivity::class.java))
                    return true
                }
                isClick = true
            }
        }
        return true
    }

    override fun onClick(v: View?) {
        context.startActivity(Intent(context, CatchNetActivity::class.java))
    }

}