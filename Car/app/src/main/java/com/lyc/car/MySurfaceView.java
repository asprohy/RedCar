package com.lyc.car;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;
import static android.graphics.BitmapFactory.decodeResource;

/**
 * Created by Administrator on 2018/1/1.
 */


class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    public static boolean isCongestionPrompt = false;
    public static boolean isPredictCongestionPrompt = false;
    public static boolean isShowCar = false;

    // 背景图
    private Bitmap BackgroundImage;

    private Bitmap bitmapCache = null;
    DrawInfoQueue dq = DrawInfoQueue.getInstance();
    //Queue<Bitmap> bitmaps = new LinkedList<Bitmap>();
    DrawInfo drawInfo = null;
    DrawInfo newDrawInfo = null;

    SurfaceHolder Holder;

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            Log.i("MyTimerTask", "run: ");
            String result = HttpConnection.getAllCarInfo();
            DrawInfo getDrawInfo = JsonTool.getDrawInfo(result);

            dq.add(getDrawInfo);
            Log.i("MyTimerTask", "run: " + dq.getDrawInfosQueue().size());
        }
    }

    public MySurfaceView(Context context, AttributeSet attrs) {

        super(context);

        BackgroundImage = decodeResource(getResources(), R.drawable.map);


        Holder = this.getHolder();// 获取holder

        Holder.addCallback(this);

    }


    @Override

    public void surfaceChanged(SurfaceHolder holder, int format, int width,

                               int height) {

        // TODO Auto-generated method stub


    }


    @Override

    public void surfaceCreated(SurfaceHolder holder) {

        // 启动自定义线程

        new Thread(new MyThread()).start();
        Timer timer = new Timer();
        timer.schedule(new MyTimerTask(), 0, 100);

    }


    @Override

    public void surfaceDestroyed(SurfaceHolder holder) {

        // TODO Auto-generated method stub


    }


    // 自定义线程类




    class MyThread implements Runnable {


        @Override


        public void run() {

            Canvas canvas = null;
            Bitmap bak = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas bakCanvas = new Canvas(bak);

            while (true) {
                try {

                    if(dq.getDrawInfosQueue().size() > 0 && drawInfo == null && newDrawInfo == null){
                        drawInfo = dq.poll();
                        //drawInfo = dq.poll();
                        newDrawInfo = drawInfo;

                    }else if(dq.getDrawInfosQueue().size() > 0) {
                        drawInfo = newDrawInfo;
                        newDrawInfo = dq.poll();
                    }

                    canvas = Holder.lockCanvas();// 获取画布

                    //Canvas pCanvas = new Canvas(BackgroundImage);
                    Paint mPaint = new Paint();
                    canvas.drawBitmap(BackgroundImage, 0, 0, mPaint);

                    if(isCongestionPrompt){
                        drawJam(canvas, newDrawInfo.getJamInfoList());
                    }

                    if(isPredictCongestionPrompt){
                        drawPreJam(canvas, newDrawInfo.getPreJamInfoList());
                    }


                    int x;
                    int y;
                    int direction;
                    //Bitmap map = BitmapFactory.decodeResource(getResources(), R.drawable.map);

                    if(drawInfo != null && newDrawInfo != null){
                        if (isShowCar == false){
                            List<Integer> changeRoadCarId = drawInfo.getChangeRoadCarIdList();
                            drawInfo.setCarInfoList(ImageHelper.carInfoInChange(drawInfo.getCarInfoList(), changeRoadCarId));
                            Log.d(TAG, "campareTwoDrawInfo: "+changeRoadCarId.get(0).toString());
                            newDrawInfo.setCarInfoList(ImageHelper.carInfoInChange(newDrawInfo.getCarInfoList(), changeRoadCarId));
                        }

                        Queue<List<CarInfo>> queue = ImageHelper.campareTwoDrawInfo(drawInfo, newDrawInfo);
                        Log.d("getCarInfosTask: Info", "" + queue.size());
                        int q = queue.peek().size();
                        Log.d(TAG, "run: q"+ q);

                        for (int i = 0; i < q; i++) {
                            //canvas.drawBitmap(BackgroundImage, 0, 0, mPaint);
                            List<CarInfo> cars = queue.poll();
                            Log.d(TAG, "run: qqq" + cars.size());
                            //Log.d("draw  ", "drawCars: BeginDrawCar" + i);

                            drawCars(canvas, cars);
                            //Holder.unlockCanvasAndPost(canvas);
                            //canvas = Holder.lockCanvas();
                            //Thread.sleep(30);
                        }


                    }
                    //canvas.drawBitmap(bak, 0, 0, null);
                    //canvas = pCanvas;

                    /*
                    List<CarInfo> cars = newDrawInfo.getCarInfoList();
                    Log.i("draw  ", "drawCars: BeginDrawCar");
                    for(int i = 0; i < cars.size(); i++) {
                        x = cars.get(i).getX() * 2;
                        y = cars.get(i).getY() * 2;
                        Log.v("x:", ""+x);
                        direction = cars.get(i).getDirection();
                        Bitmap car = getCarImage(direction);
                        canvas.drawBitmap(car, x, y, mPaint);
                        //x,y为小车中心点，绘制时减去小车图片长宽的一半
                    }
                    */

                    // 创建矩阵以控制图片旋转和平移

                    //Matrix m = new Matrix();

                    // 设置旋转角度
/*
                    m.postRotate((rotate += 48) % 360,

                            QuestionImage.getWidth() / 2,

                            QuestionImage.getHeight() / 2);

                    // 设置左边距和上边距

                    m.postTranslate(47, 47);
*/
                    // 绘制问号图

                    //canvas.drawBitmap(QuestionImage, m, mPaint);

                    // 休眠以控制最大帧频为每秒约30帧
                    //Log.d(TAG, "run: beginSleep");
                    //Thread.sleep(33);
                    //Log.d(TAG, "run: endSleep");

                } catch (Exception e) {

                } finally {
                    //Log.e(TAG, "run: postTime");
                    Holder.unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像
                }
            }

        }

        public void drawCars(Canvas canvas, List<CarInfo> cars){
            Paint mPaint = new Paint();
            int x;
            int y;
            int direction;
            for(int j = 0; j < cars.size(); j++) {
                x = cars.get(j).getX() * 2;
                y = cars.get(j).getY() * 2;
                //x = cars.get(j).getX();
                //y = cars.get(j).getY();

                x = x - 45;
                y = y - 45;
                direction = cars.get(j).getDirection();

                //Log.e(TAG, "drawCars: j: "+j+" x: "+x+" y: " + y );
                Log.e(TAG, "drawCars: carId"+ cars.get(j).getId());
                Bitmap car = getCarImage(direction);
                canvas.drawBitmap(car, x, y, mPaint);
                //x,y为小车中心点，绘制时减去小车图片长宽的一半
            }
        }


        //绘制曲线拟合线
        public void draw(Paint paint, Canvas canvas, int beginX, int beginY, int bendX, int bendY, int endX, int endY){
            Paint mPaint = new Paint();
            mPaint.setColor(paint.getColor());
            mPaint.setStrokeWidth(10);
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(12);
            //canvas.drawBitmap(BackgroundImage, 0, 0, mPaint);
            Path path = new Path();
            path.moveTo(beginX * 2, beginY * 2);
            path.quadTo(bendX * 2, bendY * 2, endX * 2, endY * 2);
            canvas.drawPath(path, mPaint);
        }


        public  void drawJam(Canvas canvas, List<JamInfo> jam){
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setStrokeWidth(12);
            paint.setAlpha(255);

            for (JamInfo j: jam) {

                if (j.getSpecialId() !=0){
                    //canvas.drawBitmap(getBendImage(j.getSpecialId()), 0, 0, paint);
                    drawBendLine(paint, canvas,j.getX(),j.getNextX(), j.getSpecialId(), j.getAngle());
                }else{
                    canvas.drawLine(j.getX() * 2, j.getY() * 2, j.getNextX() * 2, j.getNextY() * 2, paint);
                }

            }
        }

        public  void drawPreJam(Canvas canvas, List<JamInfo> jam){
            Paint paint = new Paint();

            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(12);
            paint.setAlpha(255);

            for (JamInfo j: jam) {
                //Log.d(TAG, "drawPreJam: " + j.getSpecialId());
                if (j.getSpecialId() != 0){
                    //canvas.drawBitmap(getPBendImage(j.getSpecialId()), 0, 0, mPaint);
                    drawBendLine(paint, canvas,j.getX(),j.getNextX(), j.getSpecialId(), j.getAngle());
                }else{

                    canvas.drawLine(j.getX() * 2, j.getY() * 2, j.getNextX() * 2, j.getNextY() * 2, paint);
                }
            }
            /*
            for (JamInfo j: jam){
                if (j.getSpecialId() != 0){
                    canvas.drawBitmap(getBendImage(j.getSpecialId()), 0, 0, paint);
                }
            }*/
        }

        //
        private void drawBendLine(Paint paint, Canvas canvas,int x1, int x2,  int specialId, int angle){
            int startAngle;
            int allAngle;
            Paint mPaint = new Paint();
            mPaint.setColor(paint.getColor()); //深绿色
            mPaint.setStrokeWidth(10);
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(12);
            switch (specialId){
                case 111:
                    draw(paint, canvas, 63, 124, 95, 30, 170, 95); //111
                    break;
                case 112:
                    draw(paint, canvas, 735, 544, 780, 625, 694, 643); //112
                    break;
                case 113:
                    draw(paint, canvas, 121, 643, 63, 643, 63, 584); //113
                    break;
                case 121:
                    draw(paint, canvas, 34, 124, 75, -20, 194, 80); //121
                    break;
                case 122:
                    draw(paint, canvas, 755, 523, 820, 640, 694, 672); //122
                    break;
                case 123:
                    draw(paint, canvas, 121, 672, 32, 672, 34, 584); //123
                    break;
                case 211:
                    RectF oval = new RectF(636*2, 75*2, 752*2, 191*2);
                    startAngle = -90;
                    allAngle = 90;
                    if(angle > allAngle){
                        angle = allAngle;
                    }
                    if(angle >= 0){
                        if(x2 < 0){
                            canvas.drawArc(oval, startAngle+allAngle-angle, angle, false, mPaint);
                        } else if(x1 < 0){
                            canvas.drawArc(oval, startAngle, allAngle-angle, false, mPaint);
                        } else {
                            canvas.drawArc(oval, startAngle, allAngle, false, mPaint);
                        }
                    }
                    //draw(paint, canvas, 752, 133, 752, 75, 694, 75); //211
                    break;
                case 212:
                    RectF oval2 = new RectF(122*2, 264*2, 240*2, 383*2);
                    startAngle = 134;
                    allAngle = 103;
                    if(angle > allAngle){
                        angle = allAngle;
                    }
                    if(angle >= 0){
                        if(x2 < 0){
                            canvas.drawArc(oval2, startAngle+allAngle-angle, angle, false, mPaint);
                        } else if(x1 < 0){
                            canvas.drawArc(oval2, startAngle, allAngle-angle, false, mPaint);
                        } else {
                            canvas.drawArc(oval2, startAngle, allAngle, false, mPaint);
                        }
                    }
                    //draw(paint, canvas, 149, 274, 100, 314, 139, 365); //212
                    break;
                case 213:
                    startAngle = 55;
                    allAngle = 79;
                    RectF oval3 = new RectF(324*2, 452*2, 446*2, 585*2);
                    if(angle > allAngle){
                        angle = allAngle;
                    }
                    if(angle >= 0){
                        if(x2 < 0){
                            canvas.drawArc(oval3, startAngle+allAngle-angle, angle, false, mPaint);
                        } else if(x1 < 0){
                            canvas.drawArc(oval3, startAngle, allAngle-angle, false, mPaint);
                        } else {
                            canvas.drawArc(oval3, startAngle, allAngle, false, mPaint);
                        }
                    }
                    //draw(paint, canvas, 343, 566, 379, 600, 419, 574); //213
                    break;
                case 214:
                    startAngle = 0;
                    allAngle = 55;
                    RectF oval4 = new RectF(636*2, 250*2, 752*2, 368*2);
                    if(angle > allAngle){
                        angle = allAngle;
                    }
                    if(angle >= 0){
                        if(x2 < 0){
                            canvas.drawArc(oval4, startAngle+allAngle-angle, angle, false, mPaint);
                        } else if(x1 < 0){
                            canvas.drawArc(oval4, startAngle, allAngle-angle, false, mPaint);
                        } else {
                            canvas.drawArc(oval4, startAngle, allAngle, false, mPaint);
                        }
                    }
                    //draw(paint, canvas, 727, 357, 751, 340, 752, 309); //214
                    break;
                case 221:
                    draw(paint, canvas, 781, 133, 781, 45, 694, 45); //221
                    break;
                case 222:
                    draw(paint, canvas, 133, 250, 60, 310, 119, 386); //222
                    break;
                case 223:
                    draw(paint, canvas, 322, 586, 377, 639, 436, 598); //223
                    break;
                case 224:
                    draw(paint, canvas, 744, 381, 781, 360, 781, 309); //224
                    break;
            }
        }

        //
        public void drawMyLine(Canvas canvas, int beginX, int beginY, int endX, int endY){
            Paint mPaint = new Paint();
            mPaint.setColor(Color.RED);
            mPaint.setStrokeWidth(10);
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(10);
            canvas.drawLine(beginX, beginY, endX, endY, mPaint);
        }

        public Bitmap getPBendImage(int specialId){
            Bitmap bendImage = null;
            switch (specialId){
                case 111:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.p_bend_111);
                    break;
                case 112:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.p_bend_112);
                    break;
                case 113:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.p_bend_113);
                    break;
                case 121:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.p_bend_121);
                    break;
                case 122:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.p_bend_122);
                    break;
                case 123:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.p_bend_123);
                    break;
                case 211:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.p_bend_211);
                    break;
                case 212:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.p_bend_212);
                    break;
                case 213:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.p_bend_213);
                    break;
                case 214:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.p_bend_214);
                    break;
                case 221:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.p_bend_221);
                    break;
                case 222:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.p_bend_222);
                    break;
                case 223:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.p_bend_223);
                    break;
                case 224:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.p_bend_224);
                    break;
                case 311:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.p_bend_311);
                    break;
                case 312:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.p_bend_312);
                    break;
                case 313:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.p_bend_313);
                    break;
                case 314:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.p_bend_314);
                    break;
                case 321:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.p_bend_321);
                    break;
                case 322:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.p_bend_322);
                    break;
                case 323:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.p_bend_323);
                    break;
                case 324:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.p_bend_324);
                    break;
            }
            return bendImage;
        }


        public Bitmap getBendImage(int specialId){
            Bitmap bendImage = null;
            switch (specialId){
                case 111:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.bend_111);
                    break;
                case 112:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.bend_112);
                    break;
                case 113:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.bend_113);
                    break;
                case 121:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.bend_121);
                    break;
                case 122:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.bend_122);
                    break;
                case 123:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.bend_123);
                    break;
                case 211:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.bend_211);
                    break;
                case 212:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.bend_212);
                    break;
                case 213:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.bend_213);
                    break;
                case 214:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.bend_214);
                    break;
                case 221:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.bend_221);
                    break;
                case 222:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.bend_222);
                    break;
                case 223:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.bend_223);
                    break;
                case 224:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.bend_224);
                    break;
                case 311:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.bend_311);
                    break;
                case 312:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.bend_312);
                    break;
                case 313:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.bend_313);
                    break;
                case 314:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.bend_314);
                    break;
                case 321:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.bend_321);
                    break;
                case 322:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.bend_322);
                    break;
                case 323:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.bend_323);
                    break;
                case 324:
                    bendImage =  BitmapFactory.decodeResource(getResources(), R.drawable.bend_324);
                    break;
            }

            return bendImage;
        }


        public Bitmap getCarImage(int direction){
            direction = direction / 10 * 10;
            int carLength = 45;
            Bitmap car = Bitmap.createBitmap(carLength, carLength, Bitmap.Config.ARGB_8888);
            switch (direction){
                case 0:
                    car = decodeResource(getResources(), R.drawable.red_car_0);
                    break;
                case 10:
                    car = decodeResource(getResources(), R.drawable.red_car_10);
                    break;
                case 20:
                    car = decodeResource(getResources(), R.drawable.red_car_20);
                    break;
                case 30:
                    car = decodeResource(getResources(), R.drawable.red_car_30);
                    break;
                case 40:
                    car = decodeResource(getResources(), R.drawable.red_car_40);
                    break;
                case 50:
                    car = decodeResource(getResources(), R.drawable.red_car_50);
                    break;
                case 60:
                    car = decodeResource(getResources(), R.drawable.red_car_60);
                    break;
                case 70:
                    car = decodeResource(getResources(), R.drawable.red_car_70);
                    break;
                case 80:
                    car = decodeResource(getResources(), R.drawable.red_car_80);
                    break;
                case 90:
                    car = decodeResource(getResources(), R.drawable.red_car_90);
                    break;
                case 100:
                    car = decodeResource(getResources(), R.drawable.red_car_100);
                    break;
                case 110:
                    car = decodeResource(getResources(), R.drawable.red_car_110);
                    break;
                case 120:
                    car = decodeResource(getResources(), R.drawable.red_car_120);
                    break;
                case 130:
                    car = decodeResource(getResources(), R.drawable.red_car_130);
                    break;
                case 140:
                    car = decodeResource(getResources(), R.drawable.red_car_140);
                    break;
                case 150:
                    car = decodeResource(getResources(), R.drawable.red_car_150);
                    break;
                case 160:
                    car = decodeResource(getResources(), R.drawable.red_car_160);
                    break;
                case 170:
                    car = decodeResource(getResources(), R.drawable.red_car_170);
                    break;
                case 180:
                    car = decodeResource(getResources(), R.drawable.red_car_180);
                    break;
                case 190:
                    car = decodeResource(getResources(), R.drawable.red_car_190);
                    break;
                case 200:
                    car = decodeResource(getResources(), R.drawable.red_car_200);
                    break;
                case 210:
                    car = decodeResource(getResources(), R.drawable.red_car_210);
                    break;
                case 220:
                    car = decodeResource(getResources(), R.drawable.red_car_220);
                    break;
                case 230:
                    car = decodeResource(getResources(), R.drawable.red_car_230);
                    break;
                case 240:
                    car = decodeResource(getResources(), R.drawable.red_car_240);
                    break;
                case 250:
                    car = decodeResource(getResources(), R.drawable.red_car_250);
                    break;
                case 260:
                    car = decodeResource(getResources(), R.drawable.red_car_260);
                    break;
                case 270:
                    car = decodeResource(getResources(), R.drawable.red_car_270);
                    break;
                case 280:
                    car = decodeResource(getResources(), R.drawable.red_car_280);
                    break;
                case 290:
                    car = decodeResource(getResources(), R.drawable.red_car_290);
                    break;
                case 300:
                    car = decodeResource(getResources(), R.drawable.red_car_300);
                    break;
                case 310:
                    car = decodeResource(getResources(), R.drawable.red_car_310);
                    break;
                case 320:
                    car = decodeResource(getResources(), R.drawable.red_car_320);
                    break;
                case 330:
                    car = decodeResource(getResources(), R.drawable.red_car_330);
                    break;
                case 340:
                    car = decodeResource(getResources(), R.drawable.red_car_340);
                    break;
                case 350:
                    car = decodeResource(getResources(), R.drawable.red_car_350);
                    break;
            }
            return car;
        }

    }
}