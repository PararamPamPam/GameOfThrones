package com.chenkovegor.gameofthrones.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chenkovegor.gameofthrones.R;
import com.chenkovegor.gameofthrones.data.dto.CharacterInfoDTO;
import com.chenkovegor.gameofthrones.data.storage.models.Character;
import com.chenkovegor.gameofthrones.data.storage.models.House;
import com.chenkovegor.gameofthrones.mvp.presenters.HousePresenter;
import com.chenkovegor.gameofthrones.mvp.views.IHouseView;
import com.chenkovegor.gameofthrones.mvp.views.IRootView;
import com.chenkovegor.gameofthrones.ui.activities.CharacterProfileActivity;
import com.chenkovegor.gameofthrones.ui.adapters.CharactersListAdapter;
import com.chenkovegor.gameofthrones.utils.ConstantManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListCharactersFragment extends Fragment implements IHouseView{

    private final static String TAG = ConstantManager.TAG_PREFIX;

    @BindView(R.id.character_list)
    ListView mCharactersListView;

    private HousePresenter mHousePresenter;
    private House mHouse;
    private Context mContext;

    public void setData(House house, Context context) {
        mHouse = house;
        mContext = context;
        mHousePresenter = new HousePresenter(mHouse);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "ListCharactersFragment: onCreateView");

        View view = inflater.inflate(R.layout.characters, container, false);
        ButterKnife.bind(this, view);

        mHousePresenter.takeView(this);
        mHousePresenter.initView();

        return view;
    }

    @Override
    public void onDestroyView() {
        mHousePresenter.dropView();
        super.onDestroyView();
    }

    @Override
    public void openCharacterProfile(Character cHaracter) {
        CharacterInfoDTO characterInfoDTO =
                new CharacterInfoDTO(cHaracter, mHouse.getWords());
        Intent intent = new Intent(mContext, CharacterProfileActivity.class);
        intent.putExtra(ConstantManager.CHARACTER_INFO_DTO_KEY, characterInfoDTO);
        startActivity(intent);
    }

    @Override
    public void initListView(List<Character> characters) {
        //mCharactersListView = (ListView) view.findViewById(R.id.character_list);;
        mCharactersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mHousePresenter.clickOnCharacterItem(i);
                /*CharacterInfoDTO characterInfoDTO =
                        new CharacterInfoDTO(mHouse.getMCharacters().get(i), mHouse.getWords());
                Intent intent = new Intent(mContext, CharacterProfileActivity.class);
                intent.putExtra(ConstantManager.CHARACTER_INFO_DTO_KEY, characterInfoDTO);
                startActivity(intent);*/
            }
        });

        CharactersListAdapter charactersListAdapter = new CharactersListAdapter(characters, mContext);
        mCharactersListView.setAdapter(charactersListAdapter);
    }

    @Override
    public void showMessage(String message) {
        getRootView().showMessage(message);
    }

    @Override
    public void showLoad() {
        getRootView().showLoad();
    }

    @Override
    public void hideLoad() {
        getRootView().hideLoad();
    }

    private IRootView getRootView(){
        return (IRootView) getActivity();
    }
}
