package com.lyc.car;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/8/27.
 */

public class JsonTool {
    public static DrawInfo getDrawInfo(String jsonString){
        if(jsonString == null)
            return null;
        DrawInfo drawInfo = new DrawInfo();
        try {
            //解析CarInfoList
            List<CarInfo> carInfolist = new ArrayList<CarInfo>();
            List<JamInfo> jamInfoList = new ArrayList<JamInfo>();
            List<JamInfo> preJamInfoList = new ArrayList<JamInfo>();
            List<Integer> changeRoadCarIdList = new ArrayList<>();
//            List<Integer> pathInfo = new ArrayList<Integer>();
            Log.v("jsonString", jsonString);
            JSONObject jsonObject = new JSONObject(jsonString);
            Log.v("json", jsonString);
            JSONArray jsonList = jsonObject.getJSONArray("Coordinate");
            JSONArray jsonCongestion = jsonObject.getJSONArray("Congestion");
            JSONArray jsonPreCongestion = jsonObject.getJSONArray("PreCongestion");
            JSONArray jsonShunt = jsonObject.getJSONArray("Shunt");
 //           JSONArray plan = jsonObject.getJSONArray("RoadPlan");
 //           int collision = jsonObject.getInt("Collision");
            if(jsonObject == null){
                Log.e("json", "json对象为空");
                return null;
            }

            for (int i = 0; i < jsonList.length(); i++) {
                CarInfo carInfo = new CarInfo();
                JSONObject carInfoData = jsonList.getJSONObject(i);
                if(carInfoData == null)
                {
                    break;
                }
                carInfo.setId(carInfoData.getInt("carId"));
                carInfo.setDirection(carInfoData.getInt("direction"));
                carInfo.setX(carInfoData.getInt("x"));
                carInfo.setY(carInfoData.getInt("y"));
                carInfo.setSpeed(carInfoData.getDouble("speed"));
                Log.v("x:" , "" + carInfo.getX() );
                Log.v("y:" , "" + carInfo.getY() );
                carInfolist.add(carInfo);
            }

            for (int i = 0; i < jsonCongestion.length(); i++) {
                JamInfo jamInfo = new JamInfo();
                JSONObject jamInfoData = jsonCongestion.getJSONObject(i);
                if(jamInfoData == null)
                {
                    break;
                }
                jamInfo.setX(jamInfoData.getInt("x1"));
                jamInfo.setY(jamInfoData.getInt("y1"));
                jamInfo.setNextX(jamInfoData.getInt("x2"));
                jamInfo.setNextY(jamInfoData.getInt("y2"));
                jamInfo.setSpecialId(jamInfoData.getInt("special"));
                jamInfo.setAngle(jamInfoData.getInt("deltaAngle"));
               // Log.d(TAG, "getDrawInfo: "+jamInfo.getSpecailId());
                jamInfoList.add(jamInfo);
            }

            for (int i = 0; i < jsonPreCongestion.length(); i++) {
                JamInfo jamInfo = new JamInfo();
                JSONObject jamInfoData = jsonPreCongestion.getJSONObject(i);
                if(jamInfoData == null)
                {
                    break;
                }
                jamInfo.setSpecialId(jamInfoData.getInt("special"));
                jamInfo.setX(jamInfoData.getInt("x1"));
                jamInfo.setY(jamInfoData.getInt("y1"));
                jamInfo.setNextX(jamInfoData.getInt("x2"));
                jamInfo.setNextY(jamInfoData.getInt("y2"));
                jamInfo.setAngle(jamInfoData.getInt("deltaAngle"));
                Log.d(TAG, "getDrawInfo: "+jamInfoData.getInt("special"));
                preJamInfoList.add(jamInfo);
            }

/*
            for (int i = 0; i < plan.length(); i++){
                int planId = 0;
                JSONObject jamInfoData = plan.getJSONObject(i);
                if(jamInfoData == null)
                {
                    break;
                }
                planId = jamInfoData.getInt("planId");
                pathInfo.add(planId);

            }
*/

            for (int i = 0; i < jsonShunt.length(); i++){
                int carId = 0;
                JSONObject infoData = jsonShunt.getJSONObject(i);
                if(infoData == null)
                {
                    break;
                }
                carId = infoData.getInt("shuntId");
                changeRoadCarIdList.add(carId);

            }


            drawInfo.setCarInfoList(carInfolist);
            drawInfo.setJamInfoList(jamInfoList);
            drawInfo.setPreJamInfoList(preJamInfoList);
//            drawInfo.setPathInfo(pathInfo);
//            drawInfo.setCrashInfo(collision);
            drawInfo.setChangeRoadCarIdList(changeRoadCarIdList);

            //解析小车碰撞信息

            //解析路径规划信息

            return drawInfo;

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("json", jsonString);
            Log.e("JsonParseActivity", "json解析出现了错误");
            return null;
        }

//        return null;

    }
}
