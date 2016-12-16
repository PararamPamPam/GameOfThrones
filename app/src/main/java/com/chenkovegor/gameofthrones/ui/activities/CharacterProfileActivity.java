package com.chenkovegor.gameofthrones.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chenkovegor.gameofthrones.R;
import com.chenkovegor.gameofthrones.chronos.LoadCharacterFromDbOperation;
import com.chenkovegor.gameofthrones.data.dto.CharacterInfoDTO;
import com.chenkovegor.gameofthrones.data.storage.models.Character;
import com.chenkovegor.gameofthrones.mvp.presenters.CharacterPresenter;
import com.chenkovegor.gameofthrones.mvp.views.ICharacterView;
import com.chenkovegor.gameofthrones.utils.ConstantManager;
import com.redmadrobot.chronos.ChronosConnector;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharacterProfileActivity extends BaseActivity implements ICharacterView{

    private final static String TAG = ConstantManager.TAG_PREFIX;

    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.coordinator_container)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.character_name_txt)
    TextView mCharacterNameTxt;
    @BindView(R.id.family_banner_img)
    ImageView mFamilyBannerImg;
    @BindView(R.id.words_txt)
    TextView mWordsTxt;
    @BindView(R.id.born_txt)
    TextView mBornTxt;
    @BindView(R.id.titles_txt)
    TextView mTitlesTxt;
    @BindView(R.id.aliases_txt)
    TextView mAliasesTxt;
    @BindView(R.id.pather_btn)
    Button mFatherBtn;
    @BindView(R.id.mother_btn)
    Button mMotherBtn;

    private CharacterPresenter mCharacterPresenter;
    private ChronosConnector mConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_profile_activity);
        ButterKnife.bind(this);

        CharacterInfoDTO characterInfoDTO =
                getIntent().getParcelableExtra(ConstantManager.CHARACTER_INFO_DTO_KEY);
        mCharacterPresenter = CharacterPresenter.getInstance(characterInfoDTO);

        mCharacterPresenter.takeView(this);
        mCharacterPresenter.initView();

        mConnector.onCreate(this, savedInstanceState);
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "CharacterProfileActivity: onResume");
        super.onResume();
        mConnector.onResume();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "CharacterProfileActivity: onStart");
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "CharacterProfileActivity: onSaveInstanceState");
        super.onSaveInstanceState(outState);
        mConnector.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "CharacterProfileActivity: onDestroy");
        mCharacterPresenter.dropView();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        mCharacterPresenter.leaveVIew();
        super.onBackPressed();
    }

    @Override
    public void setupToolbar() {
        setSupportActionBar(mToolBar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void setCharacterInfo(CharacterInfoDTO character) {
        mCharacterNameTxt.setText(character.getName());
        mWordsTxt.setText(character.getWords());
        mBornTxt.setText(character.getBorn());
        mTitlesTxt.setText(character.getTitles());
        mAliasesTxt.setText(character.getAliases());
        mToolBar.setTitle(character.getName());

        Integer emblemId = null;
        switch (character.getHouseName().toUpperCase()){
            case "STARKS": emblemId = R.drawable.stark;
                break;
            case "LANNISTERS": emblemId = R.drawable.lannister;
                break;
            case "TARGARYENS": emblemId = R.drawable.targarien;
                break;
        }
        if(emblemId != null){
            Picasso.with(this)
                    .load(emblemId)
                    .fit()
                    .centerCrop()
                    .into(mFamilyBannerImg);
        }
    }

    @Override
    public void setMotherName(String name) {
        mMotherBtn.setText(name);
    }

    @Override
    public void setPatherName(String name) {
        mFatherBtn.setText(name);
    }

    @Override
    public void setChronosConnector(ChronosConnector connector) {
        mConnector = connector;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoad() {
        showProgress();
    }

    @Override
    public void hideLoad() {
        hideProgress();
    }

    public void onOperationFinished(
            final LoadCharacterFromDbOperation.LoadCharacterFromDbResult result) {
        if (result.isSuccessful()) {
            mCharacterPresenter.loadParentFromDnComplete(result.getOutput());
        }
    }
}
