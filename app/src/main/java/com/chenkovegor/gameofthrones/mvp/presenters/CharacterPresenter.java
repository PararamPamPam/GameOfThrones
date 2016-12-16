package com.chenkovegor.gameofthrones.mvp.presenters;

import com.chenkovegor.gameofthrones.chronos.LoadCharacterFromDbOperation;
import com.chenkovegor.gameofthrones.data.dto.CharacterInfoDTO;
import com.chenkovegor.gameofthrones.data.storage.models.Character;
import com.chenkovegor.gameofthrones.mvp.views.ICharacterView;
import com.chenkovegor.gameofthrones.utils.ConstantManager;
import com.redmadrobot.chronos.ChronosConnector;

public class CharacterPresenter extends AbstractPresenter<ICharacterView> implements ICharacterPresenter{

    private static CharacterPresenter instance;

    private ChronosConnector mConnector;
    private CharacterInfoDTO mCharacter;
    private Character mMother;
    private Character mPather;

    private CharacterPresenter(CharacterInfoDTO character) {
        mCharacter = character;
    }

    public static CharacterPresenter getInstance(CharacterInfoDTO character) {
        if(instance == null){
            instance = new CharacterPresenter(character);
        }
        return instance;
    }

    @Override
    public void initView() {
        if(getView() != null){
            getView().showLoad();
            getView().setupToolbar();
            getView().setCharacterInfo(mCharacter);
            mConnector = new ChronosConnector();
            getView().setChronosConnector(mConnector);

            boolean isAllParentsLoaded  = true;

            if(mMother != null){
                getView().setMotherName(mMother.getName());
            }else if(mCharacter.getMother().equals("")){
                getView().setMotherName("none");
            }else{
                isAllParentsLoaded = false;
                mConnector.runOperation(new LoadCharacterFromDbOperation(mCharacter.getMother()),
                        ConstantManager.LOAD_MOTHER_FROM_DB_TAG, false);
            }

            if(mPather != null){
                getView().setPatherName(mPather.getName());
            }else if(mCharacter.getFather().equals("")){
                getView().setPatherName("none");
            }else{
                isAllParentsLoaded = false;
                mConnector.runOperation(new LoadCharacterFromDbOperation(mCharacter.getFather()),
                        ConstantManager.LOAD_FATHER_FROM_DB_TAG, false);
            }

            if(isAllParentsLoaded){
                getView().hideLoad();
            }
        }
    }

    @Override
    public void loadParentFromDnComplete(Character character) {
        if(getView() != null){
            if(character.getSearchURL().equals(mCharacter.getMother())){
                mMother = character;
                getView().setMotherName(mMother.getName());
            }else{
                mPather = character;
                getView().setPatherName(mPather.getName());
            }

            if((mCharacter.getMother().equals("") || mMother != null) &&
                    (mCharacter.getFather().equals("") || mPather != null)){
                getView().hideLoad();
            }
        }
    }

    @Override
    public void leaveVIew() {
        instance = null;
    }
}
