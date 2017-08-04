package basic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import test.car.com.myapplication.R;

public class Road{

    private static final int X = 661;
    private static final int Y = 575;

    Road(){
        Drawable drawable = getResources().getDrawable(R.drawable.map);
        BitmapDrawable bd = (BitmapDrawable) drawable;
        Bitmap bmm = bd.getBitmap();

    }

    private static Resources getResources() {
        // TODO Auto-generated method stub
        Resources mResources = null;
        mResources = getResources();
        return mResources;
    }

    public static Bitmap getMapImg() {
        Drawable drawable = getResources().getDrawable(R.drawable.map);
        BitmapDrawable bd = (BitmapDrawable) drawable;
        Bitmap bmm = bd.getBitmap();
        return bmm;
    }

}
