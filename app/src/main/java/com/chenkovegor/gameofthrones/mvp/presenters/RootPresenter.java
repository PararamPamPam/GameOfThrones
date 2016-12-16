package com.chenkovegor.gameofthrones.mvp.presenters;

import com.chenkovegor.gameofthrones.chronos.DataLoadResult;
import com.chenkovegor.gameofthrones.chronos.LoadHousesFromNetworkOperation;
import com.chenkovegor.gameofthrones.chronos.LoadHousesFromDbOperation;
import com.chenkovegor.gameofthrones.data.storage.models.House;
import com.chenkovegor.gameofthrones.mvp.models.RootModel;
import com.chenkovegor.gameofthrones.mvp.views.IRootView;
import com.chenkovegor.gameofthrones.utils.ConstantManager;
import com.redmadrobot.chronos.ChronosConnector;

import java.util.List;

public class RootPresenter extends AbstractPresenter<IRootView> implements IRootPresenter{

    private static RootPresenter instance = new RootPresenter();

    private RootModel mRootModel = new RootModel();
    private ChronosConnector mConnector;
    private List<House> mHouses;

    private RootPresenter() {
    }

    public static RootPresenter getInstance() {
        return instance;
    }

    @Override
    public void initView() {
        if(getView() != null){
            mConnector = new ChronosConnector();
            getView().setChronosConnector(mConnector);
            getView().showLoad();

            getView().setupToolbar();
            getView().setupDrawer();

            if(!mRootModel.isDataLoaded()){
                mConnector.runOperation(new LoadHousesFromNetworkOperation(),
                        ConstantManager.LOAD_HOUSES_FROM_NRTWORK_TAG, false);
            }else if(mHouses == null){
                mConnector.runOperation(new LoadHousesFromDbOperation(),
                        ConstantManager.LOAD_HOUSES_FROM_DB_TAG, false);
            }else{
                getView().initTabLayout(mHouses);
            }
        }
    }

    @Override
    public void loadHousesInfoFromNetworkComplete(DataLoadResult output) {
        if(getView() != null){
            switch (output){
                case SERVER_NOT_RESPOND:
                    getView().showServerNotRespondDialog();
                    break;
                case NETWORK_NOT_WORK:
                    getView().showNetworkNotWorkDialog();
                    break;
                case SUCCESS_COMPLETE:
                    mConnector.runOperation(new LoadHousesFromDbOperation(),
                            ConstantManager.LOAD_HOUSES_FROM_DB_TAG, false);
                    break;
            }
        }
    }

    @Override
    public void loadHousesInfoFromDBComplete(List<House> houses) {
        if(getView() != null){
            mHouses = houses;
            getView().initTabLayout(mHouses);
            getView().hideLoad();
        }
    }

    @Override
    public void changeCurrentHouse(String houseName) {
        if(getView() != null){
            for(int i = 0; i < mHouses.size(); i++){
                if(mHouses.get(i).getName().toUpperCase().equals(houseName)){
                    getView().setCurrentHouse(i);
                    break;
                }
            }
        }
    }

}
