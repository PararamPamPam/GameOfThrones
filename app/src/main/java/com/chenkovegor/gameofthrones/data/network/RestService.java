package com.chenkovegor.gameofthrones.data.network;

import com.chenkovegor.gameofthrones.data.network.res.CharacterInfoRes;
import com.chenkovegor.gameofthrones.data.network.res.HouseRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RestService {

    @GET("houses/{id}")
    Call<HouseRes> getHouseInfo(@Path("id") int id);

    @GET("characters/{id}")
    Call<CharacterInfoRes> getCharacterInfo(@Path("id") int id);
}
