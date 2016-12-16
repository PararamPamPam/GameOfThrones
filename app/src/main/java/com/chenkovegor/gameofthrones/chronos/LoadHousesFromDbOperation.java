package com.chenkovegor.gameofthrones.chronos;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chenkovegor.gameofthrones.data.managers.DataManager;
import com.chenkovegor.gameofthrones.data.storage.models.House;
import com.chenkovegor.gameofthrones.utils.ConstantManager;
import com.redmadrobot.chronos.ChronosOperation;
import com.redmadrobot.chronos.ChronosOperationResult;

import java.util.List;

public class LoadHousesFromDbOperation extends ChronosOperation<List<House>> {

    private final static String TAG = ConstantManager.TAG_PREFIX;

    @Nullable
    @Override
    public List<House> run() {
        Log.d(TAG, "LoadHousesFromDb: run");
        List<House> houses = DataManager.getInstance().getHousesFromDb();
        return houses;
    }

    @NonNull
    @Override
    public Class<? extends ChronosOperationResult<List<House>>> getResultClass() {
        return LoadHouseInfoFromDbResult.class;
    }

    public final static class LoadHouseInfoFromDbResult
            extends ChronosOperationResult<List<House>> {
    }
}
