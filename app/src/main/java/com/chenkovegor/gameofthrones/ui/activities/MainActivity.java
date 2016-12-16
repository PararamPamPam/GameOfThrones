package com.chenkovegor.gameofthrones.ui.activities;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.chenkovegor.gameofthrones.R;
import com.chenkovegor.gameofthrones.chronos.LoadHousesFromNetworkOperation;
import com.chenkovegor.gameofthrones.chronos.LoadHousesFromDbOperation;
import com.chenkovegor.gameofthrones.data.managers.Houses;
import com.chenkovegor.gameofthrones.data.storage.models.House;
import com.chenkovegor.gameofthrones.mvp.presenters.RootPresenter;
import com.chenkovegor.gameofthrones.mvp.views.IRootView;
import com.chenkovegor.gameofthrones.ui.adapters.TabFragmentsAdapter;
import com.chenkovegor.gameofthrones.utils.ConstantManager;
import com.redmadrobot.chronos.ChronosConnector;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements IRootView, NavigationView.OnNavigationItemSelectedListener{

    private final static String TAG = ConstantManager.TAG_PREFIX + "MainActivity";

    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.main_coordinator_container)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.navigation_drawer)
    DrawerLayout mNavigationDrawer;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private RootPresenter mRootPresenter = RootPresenter.getInstance();
    private ChronosConnector mConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRootPresenter.takeView(this);
        mRootPresenter.initView();

        mConnector.onCreate(this, savedInstanceState);
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        mConnector.onResume();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
        mConnector.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        mRootPresenter.dropView();
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        String houseName = "";
        switch (item.getItemId()){
            case R.id.nav_starks:
                houseName = Houses.STARKS.name();
                break;
            case R.id.nav_targaryens:
                houseName = Houses.STARKS.name();
                break;
            case R.id.nav_lannisters:
                houseName = Houses.STARKS.name();
                break;
        }
        mRootPresenter.changeCurrentHouse(houseName);
        return false;
    }

    @Override
    public void initTabLayout(List<House> houses) {
        Log.d(TAG, "initTabLayout");

        /*if(houses == null || houses.size() == 0){
            mConnector.runOperation(new LoadHousesFromDbOperation(),
                    ConstantManager.LOAD_HOUSES_FROM_DB_TAG, false);
            return;
        }*/

        TabFragmentsAdapter mTabFragmentsAdapter =
                new TabFragmentsAdapter(getSupportFragmentManager(), houses, this);
        mTabFragmentsAdapter.notifyDataSetChanged();
        mViewPager.setAdapter(mTabFragmentsAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.refreshDrawableState();
        //hideProgress();
    }

    @Override
    public void setupToolbar() {
        setSupportActionBar(mToolBar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Game  of  Thrones");

            Typeface face = Typeface.createFromAsset(getAssets(),
                    "fonts/9327.ttf");
            try {
                Field f = mToolBar.getClass().getDeclaredField("mTitleTextView");
                f.setAccessible(true);
                TextView titleTextView = (TextView) f.get(mToolBar);
                titleTextView.setTypeface(face);
            } catch (NoSuchFieldException e) {
            } catch (IllegalAccessException e) {
            }
        }
    }

    @Override
    public void setupDrawer() {
        mNavigationView.setNavigationItemSelectedListener(this);
        /*mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                mNavigationDrawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });*/
    }

    @Override
    public void setChronosConnector(ChronosConnector connector) {
        mConnector = connector;
    }

    @Override
    public void showNetworkNotWorkDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this)
                .setMessage("Проблемы с доступов в интернет")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        finish();
                    }
                });
        dialogBuilder.show();
    }

    @Override
    public void showServerNotRespondDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this)
                .setMessage("Проблемы с подключение к серверу, попробуйте позже")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        finish();
                    }
                });
        dialogBuilder.show();
    }

    @Override
    public void setCurrentHouse(int position) {
        mViewPager.setCurrentItem(position);
        mNavigationDrawer.closeDrawer(GravityCompat.START);
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
            final LoadHousesFromDbOperation.LoadHouseInfoFromDbResult result) {
        Log.d(TAG, "onOperationFinished");
        if (result.isSuccessful()) {
            mRootPresenter.loadHousesInfoFromDBComplete(result.getOutput());
            /*if(houses == null || houses.size() == 0){
                if(NetworkStatusChecker.isNetworkAvailable(this)){
                    mConnector.runOperation(new LoadHousesFromNetworkOperation(),
                            ConstantManager.LOAD_HOUSES_FROM_NRTWORK_TAG, false);
                }else{
                    setMessageProgressDialogAndShow(
                            getResources().getString(R.string.check_internet_connection));
                }
            }else{
                initTabLayout();
            }*/
        }
    }

    public void onOperationFinished(
            final LoadHousesFromNetworkOperation.LoadInfoHouseFromNetworkResult result) {
        Log.d(TAG, "onOperationFinished");
        if (result.isSuccessful()) {
            //DataLoadResult dataLoadResult = result.getOutput();
            mRootPresenter.loadHousesInfoFromNetworkComplete(result.getOutput());
            /*if(dataLoadResult == DataLoadResult.SUCCESS_COMPLETE){
                initTabLayout();
            }else{
                setMessageProgressDialogAndShow(
                        getResources().getString(R.string.server_not_respond));
            }*/
        }
    }

}
