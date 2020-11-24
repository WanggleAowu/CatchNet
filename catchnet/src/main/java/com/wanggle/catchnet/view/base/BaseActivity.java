package com.wanggle.catchnet.view.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wanggle.catchnet.R;
import com.wanggle.catchnet.utils.DensityUtil;
import com.wanggle.catchnet.utils.ScreenUtils;

/**
 * Created by Malong
 * on 19/3/11.
 * activity基类
 */
public abstract class BaseActivity extends FragmentActivity {

    protected final String TAG = this.getClass().getSimpleName();
    protected Context mContext;
    protected Activity mActivity;

    private View mStatusView;
    protected RelativeLayout mRlTitle;
    protected View mViewTitleBottom;
    protected ImageView mIvBack;
    protected TextView mTvTitle;
    protected TextView mTvOther;
    protected ImageView mIvOther;
    private View mViewMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("启动Activity  " + TAG, "onCreate: ");
        mContext = this;
        mActivity = this;
        initContentView();//加载头部布局
        dealIntent();
        initData();//请求数据
    }

    /**
     * 请求数据
     */
    protected void initData() {

    }

    /**
     * 处理前一Activity的传参
     */
    protected void dealIntent() {

    }

    /**
     * 加载头部布局
     */
    protected void initContentView() {

        setTranslucentStatus(true);//设置沉浸式状态栏

        View baseView = LayoutInflater.from(this).inflate(R.layout.activity_base_common_title, null);//添加公共头部布局
        LinearLayout llContainer = baseView.findViewById(R.id.ll_common_container);//总布局

        int statusHeight = ScreenUtils.getStatusHeight(this);//获得状态栏的高度
        mStatusView = baseView.findViewById(R.id.view_common_status);//状态栏覆盖布局
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusHeight);
        mStatusView.setLayoutParams(params);//在状态栏位置覆盖与其高度一样的布局

        mRlTitle = baseView.findViewById(R.id.rl_common_title);//头部布局
        mViewTitleBottom = baseView.findViewById(R.id.view_title_bottom);//头部下方灰线
        mIvBack = baseView.findViewById(R.id.iv_common_back);//头部左侧返回箭头
        mTvTitle = baseView.findViewById(R.id.tv_common_title);//头部中间文字
        mTvOther = baseView.findViewById(R.id.tv_common_other);//头部右侧文字
        mIvOther = baseView.findViewById(R.id.iv_common_other);//头部右侧返回图片

        mViewMain = LayoutInflater.from(this).inflate(getLayoutId(), null);
        mViewMain.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        llContainer.addView(mViewMain);
        setContentView(baseView);
        setCommonClickListener();//通用点击事件
    }

    /**
     * 通用点击事件
     */
    private void setCommonClickListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 设置沉浸式状态栏
     */
    protected void setTranslucentStatus(boolean on) {
        //沉浸式通知栏
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 获取布局ID
     *
     * @return 布局Id
     */
    protected abstract int getLayoutId();

    /**
     * 设置返回按钮
     *
     * @param resId 资源Id
     */
    protected void setIvBack(int resId) {
        mIvBack.setImageResource(resId);
    }

    /**
     * 设置返回按钮
     *
     * @param bitmap 按钮图片
     */
    protected void setIvBack(Bitmap bitmap) {
        mIvBack.setImageBitmap(bitmap);
    }

    /**
     * 设置标题
     *
     * @param title 标题文字
     */
    protected void setTitle(String title) {
        mTvTitle.setText(title);
    }

    /**
     * 设置标题
     *
     * @param resId 标题文字资源Id
     */
    public void setTitle(int resId) {
        mTvTitle.setText(resId);
    }

    /**
     * 设置右侧标题文字
     *
     * @param title 右侧标题文字
     */
    protected void setRightTitle(String title) {
        mTvOther.setText(title);
    }

    /**
     * 设置右侧标题文字资源Id
     *
     * @param resId 右侧标题文字资源Id
     */
    public void setRightTitle(int resId) {
        mTvOther.setText(resId);
    }

    /**
     * 设置右侧标题文字字体颜色
     *
     * @param color 字体颜色
     */
    public void setRightTitleColor(@ColorRes int color) {
        mTvOther.setTextColor(getResources().getColor(color));
    }

    /**
     * 设置标题栏颜色
     *
     * @param color 标题栏颜色资源id
     */
    public void setTitleColor(@ColorRes int color) {
        mRlTitle.setBackgroundResource(color);
    }

    /**
     * 设置标题栏文字颜色
     *
     * @param color 颜色资源Id
     */
    public void setTitleTextColor(@ColorRes int color) {
        mTvTitle.setTextColor(getResources().getColor(color));
    }

    /**
     * 设置标题右侧图片
     *
     * @param resId 图片资源Id
     */
    protected void setIvOther(int resId) {
        mIvOther.setImageResource(resId);
    }

    /**
     * 设置标题右侧图片
     *
     * @param bitmap 图片
     */
    protected void setIvOther(Bitmap bitmap) {
        mIvOther.setImageBitmap(bitmap);
    }

    /**
     * 设置标题高度
     *
     * @param dpHeight 标题高度，单位为dp
     */
    protected void setTitleHeight(int dpHeight) {
        int height = DensityUtil.dp2px(this, dpHeight);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        mRlTitle.setLayoutParams(params);
    }

    /**
     * 设置标题是否可见
     *
     * @param visible true:可见，false:不可见
     */
    protected void setTitleVisible(boolean visible) {
        mRlTitle.setVisibility(visible ? View.VISIBLE : View.GONE);
        mViewTitleBottom.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置状态栏颜色
     *
     * @param color 颜色资源Id
     */
    protected void setStatusColor(@ColorRes int color) {
        mStatusView.setBackgroundResource(color);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void setStatusColor(Drawable drawable) {
        mStatusView.setBackground(drawable);
    }

    /**
     * 设置状态栏是否可见
     *
     * @param visible true:可见，false:不可见
     */
    protected void setStatusVisible(boolean visible) {
        mStatusView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    protected void hideCommonTitle() {
        mRlTitle.setVisibility(View.GONE);
    }

    public void onBack(View view) {
        finish();
    }

}
