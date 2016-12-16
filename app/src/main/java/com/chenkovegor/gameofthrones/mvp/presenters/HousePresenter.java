package com.chenkovegor.gameofthrones.mvp.presenters;

import com.chenkovegor.gameofthrones.data.storage.models.House;
import com.chenkovegor.gameofthrones.mvp.views.IHouseView;

public class HousePresenter extends AbstractPresenter<IHouseView>  implements IHousePresenter{

    private House mHouse;

    public HousePresenter(House house) {
        mHouse = house;
    }

    @Override
    public void initView() {
        if(getView() != null){
            getView().initListView(mHouse.getMCharacters());
        }
    }

    @Override
    public void clickOnCharacterItem(int position) {
        if(getView() != null){
            getView().openCharacterProfile(mHouse.getMCharacters().get(position));
        }
    }
}
