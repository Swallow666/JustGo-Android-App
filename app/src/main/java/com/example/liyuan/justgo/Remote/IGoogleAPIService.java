package com.example.liyuan.justgo.Remote;

import com.example.liyuan.justgo.Model.MyPlaces;
import com.example.liyuan.justgo.Model.PlaceDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface IGoogleAPIService {
    @GET
    Call<MyPlaces> getNearByPlace(@Url String url);

    @GET
    Call<PlaceDetail> getDetailPlace(@Url String url);
}
