package connection;

import android.os.Message;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/31.
 */

public class GetInfo {
    public static void getTeamNum(){
        String originAddress = "http://192.168.1.178:8088/TServer/GetTeamNum";
        HashMap<String, String> params = new HashMap<String, String>();
        try {
            String compeletedURL = HttpUtils.getURLWithParams(originAddress, params);
            HttpUtils.sendHttpRequest(compeletedURL, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    Message message = new Message();
                    message.obj = response;
                    Log.v("getTeamNum", message.obj.toString());
                }

                @Override
                public void onError(Exception e) {
                    Message message = new Message();
                    message.obj = e.toString();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
