package com.chenkovegor.gameofthrones.mvp.presenters;

import com.chenkovegor.gameofthrones.chronos.DataLoadResult;
import com.chenkovegor.gameofthrones.data.storage.models.House;
import com.chenkovegor.gameofthrones.mvp.views.IView;

import java.util.List;

public interface IRootPresenter{

    void loadHousesInfoFromNetworkComplete(DataLoadResult output);
    void loadHousesInfoFromDBComplete(List<House> houses);
    void changeCurrentHouse(String houseName);
}
