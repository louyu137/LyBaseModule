package cn.louyu.lymvpframework.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.louyu.lymvpframework.interfaces.IView;
import cn.louyu.lymvpframework.interfaces.UIInterface;
import cn.louyu.lymvpframework.utils.annotation.Injector;
import cn.louyu.lymvpframework.utils.tools.ToastHelper;

/**
 * Created by sdj003 on 2018/9/27.
 */

public abstract class BaseFragment extends Fragment implements UIInterface,IView{

    protected View mRootView;

    /**
     * 切换刷新
     */
    protected boolean isCreated = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        Injector.inject(this,mRootView);

        initOperation();

        // 标记
        isCreated = true;

        return mRootView;
    }

    public void showLoading() {

    }

    public void hideLoading() {

    }

    public void showToast(String msg) {
        ToastHelper.showToast(getContext(),msg);
    }

    protected void showErr(String msg) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public Context getContext() {
        return this.getActivity();
    }


    /**
     * 此方法目前仅适用于标示ViewPager中的Fragment是否真实可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (!isCreated) {
            return;
        }

        if (isVisibleToUser) {

        }
    }
}
