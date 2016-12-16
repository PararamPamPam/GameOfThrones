package com.chenkovegor.gameofthrones.mvp.views;


import com.chenkovegor.gameofthrones.data.storage.models.Character;

import java.util.List;

public interface IHouseView extends IView{

    void openCharacterProfile(Character cHaracter);
    void initListView(List<Character> characters);
}
