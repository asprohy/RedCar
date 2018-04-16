package com.lyc.car;

import java.util.List;

/**
 * Created by Administrator on 2017/8/27.
 */

public class DrawInfo {
    private List<CarInfo> carInfoList;
    private List<JamInfo> jamInfoList;
    private List<JamInfo> preJamInfoList;
    private List<Integer> pathInfo;
    private int crashInfo;
    private List<Integer> changeRoadCarIdList;

    public DrawInfo(){
        carInfoList = null;
        jamInfoList = null;
        pathInfo = null;
        preJamInfoList = null;
        changeRoadCarIdList = null;
        crashInfo = 0;
    }


    public DrawInfo(List<CarInfo> carInfoList, List<JamInfo> jamInfoList, List<JamInfo> preJamInfoList, List<Integer> pathInfo, List<Integer> changeRoadCarIdList, int crashInfo) {
        this.carInfoList = carInfoList;
        this.jamInfoList = jamInfoList;
        this.preJamInfoList = preJamInfoList;
        this.pathInfo = pathInfo;
        this.changeRoadCarIdList = changeRoadCarIdList;
        this.crashInfo = crashInfo;
    }

    public List<CarInfo> getCarInfoList() {
        synchronized (this) {
            return carInfoList;
        }
    }

    public List<Integer> getChangeRoadCarIdList() {
        return changeRoadCarIdList;
    }

    public void setChangeRoadCarIdList(List<Integer> changeRoadCarIdList) {
        this.changeRoadCarIdList = changeRoadCarIdList;
    }

    public void setCarInfoList(List<CarInfo> carInfoList) {
        synchronized (this){
            this.carInfoList = carInfoList;
        }

    }

    public List<JamInfo> getPreJamInfoList() {
        return preJamInfoList;
    }

    public void setPreJamInfoList(List<JamInfo> preJamInfoList) {
        this.preJamInfoList = preJamInfoList;
    }

    public List<JamInfo> getJamInfoList() {
        return jamInfoList;
    }

    public void setJamInfoList(List<JamInfo> jamInfoList) {
        this.jamInfoList = jamInfoList;
    }

    public List<Integer> getPathInfo() {
        return pathInfo;
    }

    public void setPathInfo(List<Integer> pathInfo) {
        this.pathInfo = pathInfo;
    }

    public int getCrashInfo() {
        return crashInfo;
    }

    public void setCrashInfo(int crashInfo) {
        this.crashInfo = crashInfo;
    }

}
