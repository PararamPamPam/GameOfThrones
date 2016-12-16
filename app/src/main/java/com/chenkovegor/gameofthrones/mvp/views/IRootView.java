package com.chenkovegor.gameofthrones.mvp.views;

import com.chenkovegor.gameofthrones.data.storage.models.House;
import com.redmadrobot.chronos.ChronosConnector;

import java.util.List;

public interface IRootView extends IView{

    void setupDrawer();
    void initTabLayout(List<House> houses);
    void setupToolbar();
    void setChronosConnector(ChronosConnector connector);
    void showNetworkNotWorkDialog();
    void showServerNotRespondDialog();
    void setCurrentHouse(int position);
}
