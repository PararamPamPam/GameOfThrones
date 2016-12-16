package com.chenkovegor.gameofthrones.mvp.presenters;

import android.support.annotation.Nullable;

import com.chenkovegor.gameofthrones.chronos.DataLoadResult;
import com.chenkovegor.gameofthrones.mvp.views.IView;

public abstract class AbstractPresenter<T extends IView>{

    private T mView;

    public void takeView(T view){
        mView = view;
    }

    @Nullable
    public T getView(){
        return mView;
    }

    public void dropView(){
        mView = null;
    }

    public abstract void initView();
}
