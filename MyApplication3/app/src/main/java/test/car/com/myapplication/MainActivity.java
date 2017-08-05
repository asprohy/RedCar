package test.car.com.myapplication;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.List;

import basic.ImageHelper;
import basic.JamInfo;

public class MainActivity extends AppCompatActivity {
    int x = 55;
    int y = 300;

    boolean isPathPlanning = false;
    boolean isCongestionPrompt = false;




    Handler iHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            Bundle b = msg.getData();
            int det = b.getInt("y");
            ImageView iv = (ImageView) findViewById(R.id.map);
            Bitmap map = BitmapFactory.decodeResource(getResources(), R.drawable.map);
            Bitmap car = BitmapFactory.decodeResource(getResources(), R.drawable.red_car_270);

            if(isCongestionPrompt){
                List<JamInfo> jam = new LinkedList<JamInfo>();
                JamInfo jamInfo = new JamInfo(100, 400, 100, 800);
//                JamInfo jamInfo2 = new JamInfo(1600, 344, 1360, 716);
                jam.add(jamInfo);
//                jam.add(jamInfo2);
                map = ImageHelper.drawJamMap(map, jam);
            }

            if(isPathPlanning){
                map = ImageHelper.drawPath(map);
            }

            Bitmap show = ImageHelper.combineBitmap(map, car, x, det);
            iv.setImageBitmap(show);

            if (!map.isRecycled()) {
                map.recycle();
            }
            if (!car.isRecycled()) {
                car.recycle();
            }


        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.map);
        Bitmap car = BitmapFactory.decodeResource(getResources(), R.drawable.red_car_270);

//        Log.v("carHeight", Integer.toString(bitmap.getHeight()));
 //       Log.v("carWidth", Integer.toString(bitmap.getWidth()));

//        Message message = new Message();
 //       iHandler.sendMessage(message);
        ImageView iv = (ImageView) findViewById(R.id.map);
        bitmap = ImageHelper.drawPath(bitmap);

        iv.setImageBitmap(bitmap);



        new Thread() {
            @Override
            public void run() {
                super.run();
                for(int i = 0; i < 100; i++){
                    y = y + 10;
                    Message msg = new Message();
                    Bundle b = new Bundle();// 存放数据
                    b.putInt("y", y);
                    msg.setData(b);
                    iHandler.sendMessage(msg); // 向Handler发送消息，更新UI
                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();




        //Log.v("mapHeight", Integer.toString(bitmap.getHeight()));

    }


    //处理点击事件
    public void pathPlanningHandler(View source){
        Button button1 = (Button) findViewById(R.id.button1);

        if(isPathPlanning == false){
            button1.setText("关闭路径规划");
            isPathPlanning = true;
        }else {
            button1.setText("打开路径规划");
            isPathPlanning = false;
        }
    }


    public void congestionPromptHandler(View source){

        Button button2 = (Button) findViewById(R.id.button2);
        if(isCongestionPrompt == false){
            button2.setText("关闭拥堵提示");
            isCongestionPrompt = true;
        }else {
            button2.setText("打开拥堵提示");
            isCongestionPrompt = false;
        }
    }

    @Override
    protected void onResume() {
        //横屏
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }


}
