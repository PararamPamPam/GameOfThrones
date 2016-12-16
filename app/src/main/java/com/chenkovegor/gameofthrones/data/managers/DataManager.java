package com.chenkovegor.gameofthrones.data.managers;

import android.util.Log;
import android.widget.ShareActionProvider;

import com.chenkovegor.gameofthrones.data.network.RestService;
import com.chenkovegor.gameofthrones.data.network.ServiceGenerator;
import com.chenkovegor.gameofthrones.data.network.res.CharacterInfoRes;
import com.chenkovegor.gameofthrones.data.network.res.HouseRes;
import com.chenkovegor.gameofthrones.data.storage.models.Character;
import com.chenkovegor.gameofthrones.data.storage.models.CharacterDao;
import com.chenkovegor.gameofthrones.data.storage.models.DaoSession;
import com.chenkovegor.gameofthrones.data.storage.models.House;
import com.chenkovegor.gameofthrones.utils.App;
import com.chenkovegor.gameofthrones.utils.ConstantManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class DataManager {

    private final static String TAG = ConstantManager.TAG_PREFIX + "DataManager";

    private static DataManager ourInstance;
    private RestService mRestServise;
    private DaoSession mDaoSession;
    private Map<String, Integer> mHouses;
    private PreferencesManager mPreferencesManager;

    private DataManager() {
        mRestServise = ServiceGenerator.createService(RestService.class);
        mDaoSession = App.getDaoSession();
        mPreferencesManager = new PreferencesManager(App.getContext());
        mHouses = new HashMap<String, Integer>();
        mHouses.put("STARKS", 362);
        mHouses.put("LANNISTERS", 229);
        mHouses.put("TARGARYENS", 378);
    }

    public static DataManager getInstance(){
        if (ourInstance == null) {
            ourInstance = new DataManager();
        }
        return ourInstance;
    }

    public void dataDownloadComplete() {
        mPreferencesManager.dataDownloadComplete();
    }

    public boolean isDataDownloaded() {
        return mPreferencesManager.isDataDownloaded();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public Map<String, Integer> getHouses() {
        return mHouses;
    }

    public Call<HouseRes> loadHouseInfoFromNetwork(int houseId){
        return mRestServise.getHouseInfo(houseId);
    }

    public Call<CharacterInfoRes> loadCharacterInfoFromNetwork(int characterId){
        return mRestServise.getCharacterInfo(characterId);
    }

    public List<House> getHousesFromDb() {
        Log.d(TAG, "getHousesFromDb");
        List<House> houses = new ArrayList<>();
        houses = mDaoSession.loadAll(House.class);
        return houses;
    }

    public Character getCharacterFromDb(String characterURL){
        Log.d(TAG, "getUserListByName");
        Character character;
        character = mDaoSession.queryBuilder(Character.class)
                .where(CharacterDao.Properties.SearchURL.like("%" + characterURL.toUpperCase() + "%"))
                .build()
                .unique();
        return character;
    }
}
