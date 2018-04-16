package com.lyc.car;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/27.
 */

public class HttpHelper {

    public static String getHttpConnect(final String serverUrl){
        GetInfoThread getInfoThread = new GetInfoThread(serverUrl);
        getInfoThread.start();
        try {
            getInfoThread.join();
        }catch (Exception e){
            e.printStackTrace();
        }
        return getInfoThread.result;
    }


    //组装出带参数的完整URL
    public static String getURLWithParams(String address,HashMap<String,String> params) throws UnsupportedEncodingException {
        //设置编码
        final String encode = "UTF-8";
        StringBuilder url = new StringBuilder(address);
        url.append("?");
        //将map中的key，value构造进入URL中
        for(Map.Entry<String, String> entry:params.entrySet())
        {
            url.append(entry.getKey()).append("=");
            url.append(URLEncoder.encode(entry.getValue(), encode));
            url.append("&");
        }
        //删掉最后一个&
        url.deleteCharAt(url.length() - 1);
        return url.toString();
    }

}
