package com.chenkovegor.gameofthrones.chronos;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chenkovegor.gameofthrones.data.storage.models.Character;
import com.chenkovegor.gameofthrones.data.managers.DataManager;
import com.chenkovegor.gameofthrones.utils.ConstantManager;
import com.redmadrobot.chronos.ChronosOperation;
import com.redmadrobot.chronos.ChronosOperationResult;

public class LoadCharacterFromDbOperation extends ChronosOperation<Character> {

    private final static String TAG = ConstantManager.TAG_PREFIX;
    private String mCharacterURL;

    public LoadCharacterFromDbOperation(String characterURL) {
        mCharacterURL = characterURL.toUpperCase();
    }

    @Nullable
    @Override
    public Character run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "LoadCharacterFromDbOperation: run");
        Character character = DataManager.getInstance().getCharacterFromDb(mCharacterURL);
        return character;
    }

    @NonNull
    @Override
    public Class<? extends ChronosOperationResult<Character>> getResultClass() {
        return LoadCharacterFromDbResult.class;
    }

    public final static class LoadCharacterFromDbResult
            extends ChronosOperationResult<Character> {
    }
}
