package net.better.hongqian.common.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Hongqian.wang on 2017/9/2.
 */

public abstract class Fragment extends android.support.v4.app.Fragment {
    private View mRootView;
    private Unbinder unbinder;


    /**
     * 初始化携带的参数
     *
     * @param bundle
     * @return
     */

    protected void initArges(Bundle bundle) {
    }

    /**
     * 比onCreateView等都要早执行
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initArges(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            int layoutId = getContentLayoutId();
            //初始化当前根布局,但是不在创建时就添加到container里面 会在fragment的调度里添加到container里面去
            View root = inflater.inflate(layoutId, container, false);
            initWidget(root);
            mRootView = root;
        } else {
            //如果fragment被回收 但是mRootView还没有被回收 将其从父控件中移除
            if (mRootView.getParent() != null) {
                //把当前rootView从父控件中移除
                ((ViewGroup) mRootView.getParent()).removeView(mRootView);
            }
        }

        return mRootView;

    }

    /**Fragment
     * 当View创建完成后初始化数据
     *Fragment
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }


    /**
     * 设置当前页面的布局文件
     *
     * @return
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     */

    protected void initWidget(View root) {
        unbinder = ButterKnife.bind(this,root);
    }

    /**
     * 初始化数据
     */

    public void initData() {

    }

    /**
     * 自己定义的触发返回按键后Fragment的操作
     * 返回True 代表我已处理  Activity不用自己finish();
     * 返回False 表示你不处理 Activity自己走自己的逻辑
     *
     * @return
     */

    public boolean onBackPressd() {

        return false;
    }


}
