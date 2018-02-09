package net.better.hongqian.common.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Hongqian.wang on 2017/9/2.
 */

public abstract class Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        if (initArges(getIntent().getExtras())) {
            int layoutId = getContentLayoutId();
            setContentView(layoutId);
            initBefore();
            initWidget();
            initData();
        } else {
            finish();
        }


    }
    /**
     * 初始化控件调用之前
     */
    protected void initBefore() {

    }

    /**
     * 初始化窗口
     */
    protected void initWindow() {

    }

    /**
     * 初始化携带的参数
     *
     * @param bundle
     * @return
     */

    protected boolean initArges(Bundle bundle) {
        return true;
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

    protected void initWidget() {
        ButterKnife.bind(this);
    }

    /**
     * 初始化数据
     */

    public void initData() {

    }

    /**
     * 点击界面导航返回时,finish当前界面
     *
     * @return
     */

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        @SuppressLint("RestrictedApi")
        List<android.support.v4.app.Fragment> fragments = getSupportFragmentManager().getFragments();
        //判断是否是我们自己能够处理的返回类型
        if(fragments!=null&&fragments.size()>0) {
            for (android.support.v4.app.Fragment fragment:fragments){
                if(fragment instanceof Fragment) {
                    //如果处理 则不做任何操作
                    if(((Fragment) fragment).onBackPressd()) {
                        return;
                    }
                }
            }


        }

        super.onBackPressed();
    }

}