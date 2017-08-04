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
            canvas.drawLine(j.x, j.y, j.nextX, j.nextY, paint);
        }


        return newMap;
    }

    public static Bitmap handleImageRffect()
    {
        ColorDrawable drawable = new ColorDrawable(Color.parseColor("#FF0000"));
        Bitmap bitmap = Bitmap.createBitmap(500,500,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);

        return bitmap;
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

    public static Bitmap getCarImage(int direction){
        int carLength = 45;
        Bitmap car = Bitmap.createBitmap(carLength, carLength, Bitmap.Config.ARGB_8888);
        car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_270);
        return car;
    }

    //将所有车队的小车信息都绘制出来
    public static Bitmap drawCars(List<CarInfo> cars){
        int x;
        int y;
        int direction;
        Bitmap map = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.map);
        for(int i = 0; i < cars.size(); i++) {
            x = cars.get(i).site.x;
            y = cars.get(i).site.y;
            direction = cars.get(i).direction;
            Bitmap car = getCarImage(direction);
            map = combineBitmap(map, car, x, y);
        }
        return map;
    }

}
