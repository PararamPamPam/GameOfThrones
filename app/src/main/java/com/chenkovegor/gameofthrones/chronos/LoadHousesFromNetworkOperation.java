package com.chenkovegor.gameofthrones.chronos;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chenkovegor.gameofthrones.data.managers.DataManager;
import com.chenkovegor.gameofthrones.data.network.res.CharacterInfoRes;
import com.chenkovegor.gameofthrones.data.network.res.HouseRes;
import com.chenkovegor.gameofthrones.data.storage.models.Character;
import com.chenkovegor.gameofthrones.data.storage.models.House;
import com.chenkovegor.gameofthrones.utils.ConstantManager;
import com.redmadrobot.chronos.ChronosOperation;
import com.redmadrobot.chronos.ChronosOperationResult;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadHousesFromNetworkOperation extends ChronosOperation<DataLoadResult> {

    private final static String TAG = ConstantManager.TAG_PREFIX;
    private DataLoadResult succes;
    private List<String> charactersUrl;
    private String houseName;

    @Nullable
    @Override
    public DataLoadResult run() {
        Log.d(TAG, "LoadHousesFromNetworkOperation: run");
        Log.d(TAG, Thread.currentThread().getName());
        Map<String, Integer> housesMap = DataManager.getInstance().getHouses();
        for(String houseShortName: housesMap.keySet()){
            LoadInfoAboutHouse(houseShortName, housesMap.get(houseShortName));
            synchronized (this){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(succes == DataLoadResult.SERVER_NOT_RESPOND){
                break;
            }else{
                for(String characterUrl : charactersUrl){
                    int characterId = Integer.parseInt(characterUrl.substring(
                            characterUrl.lastIndexOf("/") + 1, characterUrl.length()));
                    LoadInfoAboutCharacter(characterId, houseName);
                    /*synchronized (this){
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }*/
                }
            }
        }
        Log.d(TAG, "LoadHousesFromNetworkOperation: run end");
        return succes;
    }

    @NonNull
    @Override
    public Class<? extends ChronosOperationResult<DataLoadResult>> getResultClass() {
        return LoadInfoHouseFromNetworkResult.class;
    }

    public final static class LoadInfoHouseFromNetworkResult
            extends ChronosOperationResult<DataLoadResult> {
    }

    private void LoadInfoAboutHouse(final String shortName, Integer houseId) {
        Log.d(TAG, "LoadInfoAboutHouse");
            Call<HouseRes> call = DataManager.getInstance().loadHouseInfoFromNetwork(houseId);
            call.enqueue(new Callback<HouseRes>() {
                @Override
                public void onResponse(Call<HouseRes> call, Response<HouseRes> response) {
                    try {
                        if (response.code() == 200) {
                            House house = new House(response.body(), shortName);
                            DataManager.getInstance().getDaoSession()
                                    .getHouseDao().insertOrReplaceInTx(house);
                            charactersUrl = response.body().getSwornMembers();
                            houseName = shortName;
                            succes = DataLoadResult.SUCCESS_COMPLETE;
                        } else {
                            Log.e(TAG, response.errorBody().source().toString());
                            succes = DataLoadResult.SERVER_NOT_RESPOND;
                        }
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                    synchronized (LoadHousesFromNetworkOperation.this){
                        LoadHousesFromNetworkOperation.this.notify();
                    }
                }
                @Override
                public void onFailure(Call<HouseRes> call, Throwable t) {
                    Log.d(TAG, t.getMessage());
                    succes = DataLoadResult.SERVER_NOT_RESPOND;
                    synchronized (LoadHousesFromNetworkOperation.this){
                        LoadHousesFromNetworkOperation.this.notify();
                    }
                }
            });
    }

    private void LoadInfoAboutCharacter(int characterId, final String houseName) {
        Log.d(TAG, "LoadInfoAboutCharacter");
            Call<CharacterInfoRes> call = DataManager.getInstance().loadCharacterInfoFromNetwork(characterId);
            call.enqueue(new Callback<CharacterInfoRes>() {
                @Override
                public void onResponse(Call<CharacterInfoRes> call, Response<CharacterInfoRes> response) {
                    try {
                        if (response.code() == 200) {
                            Log.d("PARENT", response.body().getFather() + " " + response.body().getMother());
                            Character character = new Character(response.body(), houseName);
                            DataManager.getInstance().getDaoSession()
                                    .getCharacterDao().insertOrReplaceInTx(character);
                        } else {
                            Log.e(TAG, response.errorBody().source().toString());
                        }
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                    /*synchronized (LoadHousesFromNetworkOperation.this){
                        LoadHousesFromNetworkOperation.this.notify();
                    }*/
                }

                @Override
                public void onFailure(Call<CharacterInfoRes> call, Throwable t) {
                    Log.d(TAG, t.getMessage());
                    /*synchronized (LoadHousesFromNetworkOperation.this){
                        LoadHousesFromNetworkOperation.this.notify();
                    }*/
                }
            });
    }
}
