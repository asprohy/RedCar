package com.lyc.car;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/8/28.
 */

public class GetInfoThread extends Thread {
    public String result;
    public String serverUrl;

    GetInfoThread(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Override
    public void run() {
        super.run();
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try{

            URL url = new URL(serverUrl);

            //使用HttpURLConnection
            connection = (HttpURLConnection) url.openConnection();

            //设置方法和参数
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            //获取返回结果
            InputStream inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder response = new StringBuilder();
            String line;
            Log.d(TAG, "run: 222");
            while ((line = reader.readLine()) != null){
                response.append(line);
                Log.v("backInfo", line);
            }
            Log.d(TAG, "run: " + response);
            this.result = response.toString();
//this.result = "{\"Coordinate\":[{\"carId\":1,\"direction\":316,\"speed\":0.37,\"x\":258,\"y\":561},{\"carId\":2,\"direction\":316,\"speed\":0.36,\"x\":306,\"y\":609},{\"carId\":3,\"direction\":0,\"speed\":0,\"x\":-50,\"y\":-50},{\"carId\":4,\"direction\":0,\"speed\":0,\"x\":-50,\"y\":-50}],\"Congestion\":[],\"PreCongestion\":[{\"x1\":256,\"x2\":273,\"y1\":560,\"y2\":576},{\"x1\":273,\"x2\":290,\"y1\":576,\"y2\":593},{\"x1\":290,\"x2\":307,\"y1\":593,\"y2\":609}]}";

        } catch (Exception e) {
            Log.d(TAG, "run: 与服务器连接出现异常！");
            e.printStackTrace();
        }finally {
            if(reader != null){
                try{
                    reader.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            if (connection != null){
                connection.disconnect();
            }
        }
    }
}


