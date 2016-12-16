package com.chenkovegor.gameofthrones.mvp.models;

import android.provider.ContactsContract;

import com.chenkovegor.gameofthrones.data.managers.DataManager;

public class CharacterModel {

    private DataManager mDataManager = DataManager.getInstance();

    private DataManager getDataManager() {
        return mDataManager;
    }
}
