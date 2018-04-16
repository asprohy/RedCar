package com.lyc.car;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static android.content.ContentValues.TAG;
import static com.lyc.car.MySurfaceView.isCongestionPrompt;
import static com.lyc.car.MySurfaceView.isPredictCongestionPrompt;
import static com.lyc.car.MySurfaceView.isShowCar;


public class MainActivity extends Activity {


    private MySurfaceView mapView = null;
    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_main);
        mapView = (MySurfaceView) findViewById(R.id.MySurfaceView);

    }

    @Override
    protected void onResume() {
        //横屏
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }

    public void predictCongestionPromptHandler(View source){
        Button button3 = (Button) findViewById(R.id.button3);

        if(isPredictCongestionPrompt == false && isCongestionPrompt == true){
            button3.setText("关闭预测拥堵");
            isPredictCongestionPrompt = true;
        }else if(isPredictCongestionPrompt == true && isCongestionPrompt == true){
            button3.setText("开启预测拥堵");
            isPredictCongestionPrompt = false;
        }

    }

    public void showCarHandler(View source){
        Button button1 = (Button) findViewById(R.id.button1);

        if(isShowCar == false){
            button1.setText("显示分流小车");
            Log.d(TAG, "showCarHandler: "+"allCar");
            isShowCar = true;
        }else if(isShowCar == true){
            button1.setText("显示所有小车");
            isShowCar = false;
        }

    }

    public void congestionPromptHandler(View source){

        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        if(isCongestionPrompt == false){
            button2.setText("关闭拥堵提示");
            isCongestionPrompt = true;
            button3.setVisibility(View.VISIBLE);
        }else {
            button2.setText("开启拥堵提示");
            isCongestionPrompt = false;
            isPredictCongestionPrompt = false;
            button3.setText("开启预测拥堵");
            button3.setVisibility(View.INVISIBLE);
        }
    }

}
