package com.lyc.car;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/8/28.
 */

public class ImageHelper {



    public static void drawJam(Canvas canvas, List<JamInfo> jam){
        Paint paint = new Paint();
        ImageHelper im = new ImageHelper();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setAlpha(255);

        for (JamInfo j: jam) {

            if (j.getSpecialId() !=0){
                //canvas.drawBitmap(im.getBendImage(j.getSpecialId()), 0, 0, paint);
            }else{
                canvas.drawLine(j.getX() * 2, j.getY() * 2, j.getNextX() * 2, j.getNextY() * 2, paint);
            }

        }
    }

    public static void drawPreJam(Canvas canvas, List<JamInfo> jam){
        Paint paint = new Paint();
        ImageHelper im = new ImageHelper();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);
        paint.setAlpha(100);

        for (JamInfo j: jam) {
            Log.d(TAG, "drawPreJam: " + j.getSpecialId());
            if (j.getSpecialId() != 0){
                //canvas.drawBitmap(im.getBendImage(j.getSpecialId()), 0, 0, paint);
            }else{

                canvas.drawLine(j.getX() * 2, j.getY() * 2, j.getNextX() * 2, j.getNextY() * 2, paint);
            }
        }
    }






    //绘制拥堵提示
    public static Bitmap drawJamMap(Bitmap map, List<JamInfo> jam)
    {
        if(map == null){
            return null;
        }
        int width = map.getWidth();
        int height = map.getHeight();
        Bitmap newMap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(newMap);
        canvas.drawBitmap(map, 0, 0, null);
        Paint paint = new Paint();

        paint.setColor(Color.RED);
        paint.setStrokeWidth(20);
        paint.setAlpha(100);

        for (JamInfo j: jam) {
            canvas.drawLine(j.getX() * 2, j.getY() * 2, j.getNextX() * 2, j.getNextY() * 2, paint);
        }

        return newMap;
    }




    public static Bitmap drawPreJamMap(Bitmap map, List<JamInfo> jam)
    {
        if(map == null){
            return null;
        }
        int width = map.getWidth();
        int height = map.getHeight();
        Bitmap newMap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(newMap);
        canvas.drawBitmap(map, 0, 0, null);
        Paint paint = new Paint();

        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(20);
        paint.setAlpha(100);

        for (JamInfo j: jam) {
            canvas.drawLine(j.getX() * 2, j.getY() * 2, j.getNextX() * 2, j.getNextY() * 2, paint);

        }

        return newMap;
    }



    public static Bitmap drawPath(Bitmap map, int i)
    {
        if(i == 0){
            return map;
        }
        if(map == null){
            return null;
        }
        int width = map.getWidth();
        int height = map.getHeight();
        Bitmap newMap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(newMap);
        canvas.drawBitmap(map, 0, 0, null);
        Paint paint = new Paint();

        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(20);
        paint.setAlpha(200);
        if (i == 1) {
            canvas.drawLine(210, 1434, 425, 1434, paint);
            canvas.drawLine(420, 1434, 545, 1380, paint);
            canvas.drawLine(540, 1380, 914, 920, paint);
        }

        if(i == 2){
            canvas.drawLine(500, 140, 1200, 160, paint);
            canvas.drawLine(1200, 160, 1240, 270, paint);
            canvas.drawLine(1240, 270, 1260, 320, paint);
            canvas.drawLine(1260, 320, 1270, 400, paint);
            canvas.drawLine(1270, 400, 1250, 480, paint);
            canvas.drawLine(1250, 480, 1210, 550, paint);
            canvas.drawLine(1210, 550, 1010, 800, paint);
        }

        if(i == 3) {
            canvas.drawLine(1240, 270, 1260, 320, paint);
            canvas.drawLine(1260, 320, 1270, 400, paint);
            canvas.drawLine(1270, 400, 1250, 480, paint);
            canvas.drawLine(1250, 480, 1210, 550, paint);
            canvas.drawLine(1210, 550, 1010, 800, paint);
            canvas.drawLine(1010, 800, 914, 920, paint);
            canvas.drawLine(675, 1220, 914, 920, paint);
        }

        if(i == 4){
            canvas.drawLine(2000, 520, 1440, 700, paint);
            canvas.drawLine(1450, 700, 1390, 770, paint);
            canvas.drawLine(1390, 760, 1255, 1350, paint);
            canvas.drawLine(1255, 1350, 1265, 1400, paint);

        }

        return newMap;
    }


    public static Bitmap combineBitmap(Bitmap background, Bitmap foreground, int x, int y)
    {
        if (background == null)
        {
            return null;
        }
        int bgWidth = background.getWidth();
        int bgHeight = background.getHeight();
        int fgWidth = foreground.getWidth();
        int fgHeight = foreground.getHeight();
        Bitmap newmap = Bitmap.createBitmap(bgWidth, bgHeight, Bitmap.Config.ARGB_8888);


        Canvas canvas = new Canvas(newmap);
        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawBitmap(foreground, x, y, null);
        Log.v("drawX:", ""+x);
        Log.v("drawY:", ""+y);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return newmap;
    }


    public static List<Point> meanList(List<Point> path, int steps){


        return path;
    }

    //测试这个
    public static List<DrawInfo> campareTwoDrawInfoRList(DrawInfo drawInfo, DrawInfo newDrawInfo) {
        List<CarInfo> carInfos = drawInfo.getCarInfoList();
        List<CarInfo> newCarInofs = newDrawInfo.getCarInfoList();
        List<DrawInfo> carInfosQueue = new LinkedList<DrawInfo>();
        List<CarInfo> carInfosList = new LinkedList<>();
        List<Integer> changeRoadCarId = drawInfo.getChangeRoadCarIdList();
        //只显示分列小车
        int countForCompare = 0;
        if (carInfos.size() == newCarInofs.size()){
            for(int i = 0; i < carInfos.size(); i++){
                if(carInfos.get(i).getX() == newCarInofs.get(i).getX()){
                    countForCompare = countForCompare + 1;
                }
            }
        }

        Log.e(TAG, "campareTwoDrawInfo: countForCompare: "+ countForCompare);
        if (carInfos.size() == countForCompare){
            carInfosQueue.add(newDrawInfo);
            return carInfosQueue;
        }

        //改到这里了

/*
        for(CarInfo carInfo: newCarInofs){
            carInfo.setX(carInfo.getX() * 2 - 45);
            carInfo.setY(carInfo.getY() * 2 - 45);
            //carInfo.showCarInfo(carInfo);
        }
*/
/*
        if(MySurfaceView.isShowCar == true) {
            Log.d(TAG, "campareTwoDrawInfo: TRUE"+changeRoadCarId.get(0).toString());
        }else{
            Log.d(TAG, "campareTwoDrawInfo: FLASE"+changeRoadCarId.get(0).toString());
        }

        if(MySurfaceView.isShowCar == false){
            carInfos = carInfoInChange(carInfos, changeRoadCarId);
            Log.d(TAG, "campareTwoDrawInfo: "+changeRoadCarId.get(0).toString());
            newCarInofs = carInfoInChange(newCarInofs, changeRoadCarId);
        }
*/

        int count = 0;
        int steps = 5;
        for(CarInfo carInfo: carInfos){
            for (CarInfo newCarInfo : newCarInofs){
                if(carInfo.getId() == newCarInfo.getId()){

                    count ++;

                    int x1 = carInfo.getX();
                    int y1 = carInfo.getY();
                    int x2 = newCarInfo.getX();
                    int y2 = newCarInfo.getY();
                    List<Point> path = separateLine(steps, x1, y1, x2, y2);
                    for(int i = 0; i < path.size(); i++){
                        CarInfo insertCarInfo = new CarInfo();
                        insertCarInfo.setX(path.get(i).getX());
                        insertCarInfo.setY(path.get(i).getY());
                        Log.e(TAG, "campareTwoDrawInfo: inSertCarInfoX" + insertCarInfo.getX());
                        Log.e(TAG, "campareTwoDrawInfo: inSertCarInfoY" + insertCarInfo.getY());
                        carInfosList.add(insertCarInfo);
                    }
                    Log.e(TAG, "campareTwoDrawInfo: "+carInfosList.size() );
                }

            }
        }
        Log.e(TAG, "campareTwoDrawInfo: carInfoListSize" + carInfosList.size());

        for(int i = 0; i < steps + 1; i++) {
            List<CarInfo> carInfos1 = new LinkedList<CarInfo>();
            for(int j = 0; j < count; j++){
                carInfos1.add(carInfosList.get(i + j * (steps+1)));
                newDrawInfo.setCarInfoList(carInfos1);
                carInfosQueue.add(newDrawInfo);
            }
        }
        return carInfosQueue;
    }

    public static Queue<List<CarInfo>> campareTwoDrawInfo(DrawInfo drawInfo, DrawInfo newDrawInfo){
        List<CarInfo> carInfos = drawInfo.getCarInfoList();
        List<CarInfo> newCarInofs = newDrawInfo.getCarInfoList();
        Queue<List<CarInfo>> carInfosQueue = new LinkedList<List<CarInfo>>();
        List<CarInfo> carInfosList = new LinkedList<>();
        List<Integer> changeRoadCarId = drawInfo.getChangeRoadCarIdList();
        //只显示分列小车
        int countForCompare = 0;
        if (carInfos.size() == newCarInofs.size()){
            for(int i = 0; i < carInfos.size(); i++){
                if(carInfos.get(i).getX() == newCarInofs.get(i).getX()){
                    countForCompare = countForCompare + 1;
                }
            }
        }



        Log.e(TAG, "campareTwoDrawInfo: countForCompare: "+ countForCompare);
        if (carInfos.size() == countForCompare){
            carInfosQueue.add(newCarInofs);
            return carInfosQueue;
        }

/*
        for(CarInfo carInfo: newCarInofs){
            carInfo.setX(carInfo.getX() * 2 - 45);
            carInfo.setY(carInfo.getY() * 2 - 45);
            //carInfo.showCarInfo(carInfo);
        }
*/
/*
        if(MySurfaceView.isShowCar == true) {
            Log.d(TAG, "campareTwoDrawInfo: TRUE"+changeRoadCarId.get(0).toString());
        }else{
            Log.d(TAG, "campareTwoDrawInfo: FLASE"+changeRoadCarId.get(0).toString());
        }

        if(MySurfaceView.isShowCar == false){
            carInfos = carInfoInChange(carInfos, changeRoadCarId);
            Log.d(TAG, "campareTwoDrawInfo: "+changeRoadCarId.get(0).toString());
            newCarInofs = carInfoInChange(newCarInofs, changeRoadCarId);
        }
*/

        int count = 0;
        int steps = 5;
        for(CarInfo carInfo: carInfos){
            for (CarInfo newCarInfo : newCarInofs){
                if(carInfo.getId() == newCarInfo.getId()){
                    count ++;
/*
                    int x1 = carInfo.getX();
                    int y1 = carInfo.getY();
                    int x2 = newCarInfo.getX();
                    int y2 = newCarInfo.getY();
                    List<Point> path = separateLine(steps, x1, y1, x2, y2);
                    for(int i = 0; i < path.size(); i++){
                        CarInfo insertCarInfo = new CarInfo();
                        insertCarInfo.setX(path.get(i).getX());
                        insertCarInfo.setY(path.get(i).getY());
                        Log.e(TAG, "campareTwoDrawInfo: inSertCarInfoX" + insertCarInfo.getX());
                        Log.e(TAG, "campareTwoDrawInfo: inSertCarInfoY" + insertCarInfo.getY());
                        carInfosList.add(insertCarInfo);
                    }
                    Log.e(TAG, "campareTwoDrawInfo: "+carInfosList.size() );
*/

                    int detaX = (newCarInfo.getX() - carInfo.getX());
                    int detaY = (newCarInfo.getY() - carInfo.getY());

                    Log.e(TAG, "campareTwoDrawInfo" + count + ":newCarInfoX: "+ newCarInfo.getX()+" carInfoX: "+carInfo.getX()+"detaX: "+detaX );
                    Log.e(TAG, "campareTwoDrawInfo" + count + ":newCarInfoY: "+ newCarInfo.getY()+" carInfoY: "+carInfo.getY()+"detaY: "+detaY );

                    //int detaX = 10;
                    //int detaY = 10;
                    //double rate = (820 * 2) / 700;
                    //Log.e("rate: ", "" + rate);
                    //Log.e("DetaX: ", " "+ (double)(detaX * rate));
                    //Log.e("DetaY: ", " "+ (double)(detaY * rate));
                    double dStepX = (double) detaX / steps;
                    double dStepY = (double) detaY / steps;
                    Log.e(TAG, "campareTwoDrawInfo: dStepX" + dStepX);
                    Log.e(TAG, "campareTwoDrawInfo: dStepY" + dStepY);
                    int stepX = (int)Math.round(dStepX);
                    int stepY = (int)Math.round(dStepY);
                    Log.e("StepX: ", ""+ stepX);
                    Log.e("StepY: ", ""+ stepY);
                    CarInfo insertCarInfo = carInfo;
                    carInfosList.add(insertCarInfo);
                    for(int i = 0; i < steps; i++){
                        insertCarInfo.setX(insertCarInfo.getX() + stepX);
                        insertCarInfo.setY(insertCarInfo.getY() + stepY);
                        Log.e(TAG, "campareTwoDrawInfo: inSertCarInfoX" + insertCarInfo.getX());
                        Log.e(TAG, "campareTwoDrawInfo: inSertCarInfoY" + insertCarInfo.getY());
                        carInfosList.add(insertCarInfo);
                    }
                    Log.e(TAG, "campareTwoDrawInfo: "+carInfosList.size() );

                }

            }
        }
        Log.e(TAG, "campareTwoDrawInfo: carInfoListSize" + carInfosList.size());

        for(int i = 0; i < steps + 1; i++) {
            List<CarInfo> carInfos1 = new LinkedList<CarInfo>();
            for(int j = 0; j < count; j++){
                carInfos1.add(carInfosList.get(i + j * (steps+1)));
            }
            carInfosQueue.add(carInfos1);
        }


        return carInfosQueue;
    }

    public static List<Point> separateLine(int steps, int x1, int y1, int x2, int y2){
        Log.e(TAG, "separateLine: beginSeparate" );
        List<Point> sLine = new ArrayList<Point>();

        sLine.add(addPoint(x1,y1));//加入起点值

        List<Point> mLine =drawLine(x1,y1,x2,y2) ;//存放两个坐标之间的所有点

        int num =mLine.size();//两个坐标之间所有点的个数


        if(steps > num){
            //steps大于可用点的情况
            for(int i = 1;i <= (steps - 2);i++){
                int index = num - 1;
                int avg_index = Math.round((index*i)/(steps - 1));
                sLine.add(mLine.get(avg_index));

            }
        }else{
            for(int i = 1;i <= (steps - 2);i++){
                int index = num - 1;
                int avg_index = (index*i)/(steps - 1);
                sLine.add(mLine.get(avg_index));
            }
        }
        sLine.add(addPoint(x2,y2));//加入终点值
        Log.e(TAG, "separateLine: endSeparate" );
        //System.out.println(sLine.size());
        return sLine;
    }

    public static List<Point> drawLine(int x1, int y1, int x2, int y2){
        List<Point> line = new ArrayList<Point>();
        int x = x1,y = y1;
        int a = y1 - y2,b = x2 - x1;//构造判别式 ax+by+c = 0,代入(x1,y1),(x2,y2)可得a，b
        int d;
        int delta1,delta2;

        line.add(addPoint(x1,y1));
        //起点加入list中
        if(Math.abs(a/b) <= 1){
            //斜率绝对值小于等于1的情况
            d = 2*a+b;//d = a+0.5b,避免小数计算，用2d代替d.
            delta1 = 2*a;
            delta2 =2*(a+b);
            while(x < x2){
                if(d < 0){
                    x++;
                    y++;
                    d += delta2;
                }
                else{
                    x++;
                    d += delta1;
                }
                line.add(addPoint(x,y));

            }
        }
        else{
            d = a+2*b;
            delta1 = 2*b;
            delta2 = 2*(a+b);
            while(y<y2){
                if(d < 0){
                    y++;
                    d += delta1;
                }
                else{
                    x++;
                    y++;
                    d += delta2;
                }
                line.add(addPoint(x,y));
            }
        }

        //line.add(addPoint(x2,y2));//终点加入List.
        return line;
    }

    public static Point addPoint(int mid_x,int mid_y){
        Point midPoint = new Point();
        midPoint.setX(mid_x);
        midPoint.setY(mid_y);
        return midPoint;
    }

    //需求方说要把正常行驶的小车屏蔽掉，然后在小车进入变道后重新显示小车
    //这个函数是从服务器获取哪些小车需要显示后，将需要显示的小车从链表中取出来进行显示，别的就相应进行了屏蔽
    //因此这个函数需要输入一个carInfo的List 还有一个需要显示的小车ID
    public static List<CarInfo> carInfoInChange(List<CarInfo> carInfoList, List<Integer> carIdList){
        List<CarInfo> carInfosInChange = new LinkedList<CarInfo>();

        //找出有的carInfo
        for (int id: carIdList){

            for(int i = 0; i < carInfoList.size(); i++){
                CarInfo carInfo = carInfoList.get(i);
                //carInfo.showCarInfo(carInfo);
                Log.d(TAG, "carInfoInChange: id " + id + " i " + i +" carId " + carInfo.getId() + " carInfoSize "+carInfoList.size());
                if(id == carInfo.getId()){
                    carInfosInChange.add(carInfo);
                    Log.d(TAG, "carInfoInChange: "+ "addSuccess");
                }
            }
        }
        Log.d(TAG, "carInfoInChange: "+carInfosInChange.size());
        return carInfosInChange;
    }

}
