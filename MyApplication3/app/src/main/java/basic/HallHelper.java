package basic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/5.
 */

public class HallHelper {

    public static List<HallInfo> specialHall= new LinkedList<HallInfo>();
    public static List<HallInfo> hallList = new LinkedList<HallInfo>();
    public static List<BendInfo> bendInfoList = new LinkedList<BendInfo>();

    HallHelper(){
        getBendInfoList();
        getHallList();
        getSpecialHall();
    }

    public static List<JamInfo> hallToJam(Hall beginHall, Hall endHall){
        List<JamInfo> jamInfoList = new ArrayList<>();
        if(beginHall.hallCount > endHall.hallCount){

        }else if(beginHall.hallCount < endHall.hallCount){
            for(; beginHall.hallCount < endHall.hallCount; beginHall.hallCount++){
                if(isSpecialHall(beginHall)){
                    int maxNum = getMaxSpNumber(beginHall);
                    Site thisSite = getSite(beginHall);
                    Site nextSite = getSpSite(beginHall, 0);
                    JamInfo jamInfo = new JamInfo(thisSite.x, thisSite.y, nextSite.x, nextSite.y);
                    jamInfoList.add(jamInfo);
                    for(int i = 1; i < maxNum; i++){
                        thisSite = nextSite;
                        nextSite = getSpSite(beginHall, i * 10);
                        JamInfo jamInfo2 = new JamInfo(thisSite.x, thisSite.y, nextSite.x, nextSite.y);
                        jamInfoList.add(jamInfo2);
                    }
                    thisSite = nextSite;
                    nextSite = getSite(getNextHall(beginHall));
                    JamInfo jamInfo2 = new JamInfo(thisSite.x, thisSite.y, nextSite.x, nextSite.y);
                    jamInfoList.add(jamInfo2);

                }else{
                    Hall nextHall = getNextHall(beginHall);
                    Site thisSite = getSite(beginHall);
                    Site nextSite = getSite(nextHall);
                    JamInfo jamInfo = new JamInfo(thisSite.x, thisSite.y, nextSite.x, nextSite.y);
                    jamInfoList.add(jamInfo);
                }
            }
        }

        return jamInfoList;
    }

    public static Site getSpSite(Hall hall, int distance){
        Site site = new Site();
        //内道1， 外道2
        int channel = 1;
        if(hall.channel.equals("outside")){
            channel = 2;
        }else{
            channel = 1;
        }

        for(BendInfo bendInfo: bendInfoList){
            if(hall.roadId == bendInfo.roadId
                    && hall.hallCount == bendInfo.hallCount
                    && channel == bendInfo.channel
                    && bendInfo.distance == distance){
                site.x = bendInfo.x;
                site.y = bendInfo.y;
            }
        }
        return site;
    }

    public static int getMaxSpNumber(Hall hall){
        int maxNum = 0;

        //内道1， 外道2
        int channel = 1;
        if(hall.channel.equals("outside")){
            channel = 2;
        }else{
            channel = 1;
        }

        for(BendInfo bendInfo: bendInfoList){
            if(hall.roadId == bendInfo.roadId
                    && hall.hallCount == bendInfo.hallCount
                    && channel == bendInfo.channel
                    && bendInfo.distance > maxNum){
                maxNum = bendInfo.distance;
            }
        }
        maxNum = maxNum / 10;
        return maxNum;
    }

    public static Site getSite(Hall hall){
        Site site = new Site();
        for(HallInfo hallInfo: hallList){
            if(hall.channel.equals(hallInfo.channel)
                    && hall.hallCount == hallInfo.hallCount
                    && hall.roadId == hallInfo.roadId){
                site.x = hallInfo.x;
                site.y = hallInfo.y;
            }
        }
        return site;

    }

    public static boolean isSpecialHall(Hall thisHall){
        for(BendInfo bendInfo : bendInfoList){
            //内道1， 外道2
            int channel = 1;
            if(thisHall.channel.equals("outside")){
                channel = 2;
            }else{
                channel = 1;
            }
            if(bendInfo.hallCount == thisHall.hallCount
                    && bendInfo.roadId == thisHall.roadId
                    && channel == bendInfo.channel){
                return true;
            }
        }
        return false;
    }

    public static Hall getNextHall(Hall thisHall){
        if(thisHall.hallCount == getMaxHallCount(thisHall)){
            for(HallInfo h: hallList){
                if(h.hallCount == 1
                        && h.roadId == thisHall.roadId
                        && h.channel.equals(thisHall.channel)){
                    Hall hall = new Hall(h.roadId, h.hallCount, h.channel);
                    return hall;
                }
            }
        }else{
            for(HallInfo h: hallList){
                if(h.hallCount == thisHall.hallCount + 1
                        && h.roadId == thisHall.roadId
                        && h.channel.equals(thisHall.channel)){
                    Hall hall = new Hall(h.roadId, h.hallCount, h.channel);
                    return hall;
                }
            }
        }
        return thisHall;
    }

    public static int getMaxHallCount(Hall h) {
        int maxHallCount = 0;
        for (HallInfo hallInList : hallList) {
            if (hallInList.roadId == h.roadId
                    && hallInList.hallCount == h.hallCount
                    && hallInList.channel.equals(h.channel)) {
                if (hallInList.hallCount > maxHallCount) {
                    maxHallCount = hallInList.hallCount;
                }
            }
        }
        return maxHallCount;
    }

    public static void getSpecialHall(){

    }

    public static void getHallList(){

    }

    public static void getBendInfoList(){

    }


}
