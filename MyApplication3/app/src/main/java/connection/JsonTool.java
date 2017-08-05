package connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import basic.CarInfo;
import basic.JamInfo;

/**
 * Created by Administrator on 2017/8/1.
 */

public class JsonTool {


    //解析单个小车信息
    public static CarInfo getCarInfo(String key, String jsonString)
    {
        CarInfo carInfo = new CarInfo();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject jsonObject2 = jsonObject.getJSONObject(key);
            carInfo.setX(jsonObject2.getInt("x"));
            carInfo.setY(jsonObject2.getInt("y"));
            carInfo.setDirection(jsonObject2.getInt("direction"));

        }catch (JSONException e){
            e.printStackTrace();
        }
        return carInfo;
    }

    //解析所有小车信息
    public static List<CarInfo> getCarInfos(String key, String jsonString){
        List<CarInfo> list = new ArrayList<CarInfo>();
        JSONObject jsonObject;
        try{
            jsonObject = new JSONObject(jsonString);
            JSONArray CarInfos = jsonObject.getJSONArray(key);
            for(int i = 0; i < CarInfos.length(); i++){
                CarInfo carInfo = new CarInfo();
                JSONObject jsonObject2 = CarInfos.getJSONObject(i);
                carInfo.setX(jsonObject2.getInt("x"));
                carInfo.setY(jsonObject2.getInt("y"));
                carInfo.setDirection(jsonObject2.getInt("direction"));
            }


        }catch (JSONException e){
            e.printStackTrace();
        }

        return list;
    }

    //解析拥堵数据
    public static List<JamInfo> getJamInfo(String key, String jsonString){
        List<JamInfo> list = new ArrayList<JamInfo>();
        JSONObject jsonObject;
        try{
            jsonObject = new JSONObject(jsonString);
            JSONArray JamInfos = jsonObject.getJSONArray(key);
            for(int i = 0; i < JamInfos.length(); i++){
                JamInfo jamInfo = new JamInfo();
                JSONObject jsonObject2 = JamInfos.getJSONObject(i);
                jamInfo.setX(jsonObject2.getInt("x"));
                jamInfo.setY(jsonObject2.getInt("y"));
                jamInfo.setNextX(jsonObject2.getInt("nextX"));
                jamInfo.setNextY(jsonObject2.getInt("nextY"));
            }


        }catch (JSONException e){
            e.printStackTrace();
        }

        return list;
    }


}
