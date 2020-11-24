package com.wanggle.catchnet.view.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import org.jetbrains.annotations.Nullable;

/**
 * Created by Malong
 * on 19/3/11.
 * ftagment 基类
 */
public abstract class BaseFragment extends Fragment {

    public View view;
    public Context mContext;
    private RelativeLayout mRlTitle;
    protected View mViewTitleBottom;
    protected ImageView mIvBack;
    protected TextView mTvTitle;
    protected TextView mTvOther;
    private ImageView mIvOther;

    private View mViewMain;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        view = initContentView();
        setTranslucentStatus(true);
        this.initData();
        return view;
    }

    /**
     * 加载头部布局
     */
    protected View initContentView() {

        View baseView = LayoutInflater.from(mContext).inflate(R.layout.activity_base_common_title, null);//添加公共头部布局
        LinearLayout llContainer = baseView.findViewById(R.id.ll_common_container);//总布局

        mRlTitle = baseView.findViewById(R.id.rl_common_title);//头部布局
        mRlTitle.setVisibility(View.GONE);

        mViewMain = LayoutInflater.from(mContext).inflate(getLayoutId(), null);
        mViewMain.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        llContainer.addView(mViewMain);
        return baseView;
    }

    /**
     * 设置沉浸式状态栏
     */
    protected void setTranslucentStatus(boolean on) {
        //沉浸式通知栏
        Window win = getActivity().getWindow();
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
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 数据初始化操作
     */
    protected void initData() {
    }

    public void showToast(String str) {
    }

    public void showLongToast(String str) {
    }

    /**
     * 重新加载方法
     */
    protected void retry() {

    }

}
