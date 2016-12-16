package com.chenkovegor.gameofthrones.mvp.models;

import com.chenkovegor.gameofthrones.data.managers.DataManager;

public class RootModel {

    private DataManager mDataManager = DataManager.getInstance();

    public boolean isDataLoaded(){
        return mDataManager.isDataDownloaded();
    }

    public void dataDownloadComplete(){
        mDataManager.dataDownloadComplete();
    }

}
