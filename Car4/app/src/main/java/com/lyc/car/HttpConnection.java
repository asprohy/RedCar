package com.lyc.car;

import android.util.Log;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/8/28.
 */

public class HttpConnection {

    public static String getAllCarInfo(){
        //String originAddress = "http://192.168.1.178:8088/TServer/GetStateInfo";
        String originAddress = "http://192.168.1.7:8080/TServer/GetStateInfo";
        //String originAddress = "http://192.168.3.25:8080/TServer/GetStateInfo";
        HashMap<String, String> params = new HashMap<String, String>();

        String result = null;

        String carId = "all";
        String congestion = "congestion";
        String preCongestion = "preCongestion";
//        String plan = "plan";
 //       String collision = "collision";

        params.put("carId", carId);
        params.put("congestion", congestion);
        params.put("preCongestion", preCongestion);

//        params.put("plan", plan);
//        params.put("collision", collision);
        try{
            String compeletedURL = HttpHelper.getURLWithParams(originAddress, params);
            Log.d(TAG, "getAllCarInfo: URL"+ compeletedURL);
            result = HttpHelper.getHttpConnect(compeletedURL);
            Log.d(TAG, "getAllCarInfo: Result"+result);
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
}
