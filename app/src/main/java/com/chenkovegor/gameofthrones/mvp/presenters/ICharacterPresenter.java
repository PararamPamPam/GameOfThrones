package com.chenkovegor.gameofthrones.mvp.presenters;

import com.chenkovegor.gameofthrones.data.storage.models.Character;
import com.chenkovegor.gameofthrones.mvp.views.IView;

public interface ICharacterPresenter{

    void loadParentFromDnComplete(Character character);
    void leaveVIew();
}
