package test.car.com.myapplication;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Administrator on 2017/7/21.
 */

public class test {
    public static void foo(){
 //       Resources resources = Resources.getSystem();
        Bitmap car = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.red_car_270);
  //      Log.v("car", Integer.toString(car.getHeight()));
    }
}
