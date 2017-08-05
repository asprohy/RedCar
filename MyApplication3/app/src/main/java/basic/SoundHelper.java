package basic;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by Administrator on 2017/8/4.
 */

public class SoundHelper {




    //播放声音
    public static void playSound(String path) {
        MediaPlayer player = new MediaPlayer();
        try {
            player.setDataSource(path);
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
