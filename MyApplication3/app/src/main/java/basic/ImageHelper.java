package basic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;

import java.util.List;

import test.car.com.myapplication.R;

/**
 * Created by Administrator on 2017/7/17.
 */

public class ImageHelper {

    public static Bitmap handleImageRffect(Bitmap bm, int hue, int saturatuon, int lum)
    {
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        ColorMatrix hueMatrix = new ColorMatrix();
        hueMatrix.setRotate(0, hue);
        hueMatrix.setRotate(1, hue);
        hueMatrix.setRotate(2, hue);

        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturatuon);

        ColorMatrix lumMatrix = new ColorMatrix();
        lumMatrix.setScale(lum, lum, lum, 1);

        ColorMatrix imageMatrix = new ColorMatrix();
        imageMatrix.postConcat(hueMatrix);
        imageMatrix.postConcat(saturationMatrix);
        imageMatrix.postConcat(lumMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));
        canvas.drawBitmap(bm, 0, 0, paint);

        return bmp;
    }

    public static Bitmap handleImageRffect()
    {
        ColorDrawable drawable = new ColorDrawable(Color.parseColor("#FF0000"));
        Bitmap bitmap = Bitmap.createBitmap(500,500,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);

        return bitmap;
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
            canvas.drawLine(j.getX(), j.getY(), j.getNextX(), j.getNextY(), paint);
        }

        return newMap;
    }

    public static Bitmap drawPath(Bitmap map)
    {
        int i = 4;
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

        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return newmap;
    }




    //将所有车队的小车信息都绘制出来
    public static Bitmap drawCars(List<CarInfo> cars){
        int x;
        int y;
        int direction;
        Bitmap map = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.map);
        for(int i = 0; i < cars.size(); i++) {
            x = cars.get(i).getX();
            y = cars.get(i).getY();
            direction = cars.get(i).getDirection();
            Bitmap car = getCarImage(direction);

            //x,y为小车中心点，绘制时减去小车图片长宽的一半
            map = combineBitmap(map, car, x - 22, y - 22);
        }
        return map;
    }



    //获取小车图片，放在最后面，我不想看到这个方法
    public static Bitmap getCarImage(int direction){
        int carLength = 45;

        Bitmap car = Bitmap.createBitmap(carLength, carLength, Bitmap.Config.ARGB_8888);
        switch (direction){
            case 0:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_0);
                break;
            case 10:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_10);
                break;
            case 20:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_20);
                break;
            case 30:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_30);
                break;
            case 40:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_40);
                break;
            case 50:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_50);
                break;
            case 60:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_60);
                break;
            case 70:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_70);
                break;
            case 80:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_80);
                break;
            case 90:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_90);
                break;
            case 100:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_100);
                break;
            case 110:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_110);
                break;
            case 120:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_120);
                break;
            case 130:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_130);
                break;
            case 140:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_140);
                break;
            case 150:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_150);
                break;
            case 160:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_160);
                break;
            case 170:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_170);
                break;
            case 180:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_180);
                break;
            case 190:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_190);
                break;
            case 200:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_200);
                break;
            case 210:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_210);
                break;
            case 220:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_220);
                break;
            case 230:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_230);
                break;
            case 240:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_240);
                break;
            case 250:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_250);
                break;
            case 260:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_260);
                break;
            case 270:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_270);
                break;
            case 280:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_280);
                break;
            case 290:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_290);
                break;
            case 300:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_300);
                break;
            case 310:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_310);
                break;
            case 320:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_320);
                break;
            case 330:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_330);
                break;
            case 340:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_340);
                break;
            case 350:
                car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_350);
                break;
        }

        return car;
    }
}
