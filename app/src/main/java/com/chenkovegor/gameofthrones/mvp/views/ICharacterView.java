package com.chenkovegor.gameofthrones.mvp.views;

import com.chenkovegor.gameofthrones.data.dto.CharacterInfoDTO;
import com.redmadrobot.chronos.ChronosConnector;

public interface ICharacterView extends IView{

    void setupToolbar();
    void setCharacterInfo(CharacterInfoDTO character);

    void setMotherName(String name);
    void setPatherName(String name);

    void setChronosConnector(ChronosConnector connector);
}
