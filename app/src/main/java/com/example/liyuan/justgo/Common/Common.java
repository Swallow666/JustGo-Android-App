package com.example.liyuan.justgo.Common;

import com.example.liyuan.justgo.Model.Results;
import com.example.liyuan.justgo.Remote.IGoogleAPIService;
import com.example.liyuan.justgo.Remote.RetrofitClient;


public class Common {

    public static Results currentResult;

    private static final String GOOGLE_API_URL = "https://maps.googleapis.com/";
    public static IGoogleAPIService getGoogleAPIService(){
        return RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleAPIService.class);
    }
}
