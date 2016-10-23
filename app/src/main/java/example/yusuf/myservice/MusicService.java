package example.yusuf.myservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Yusuf on 2016/10/23.
 */

public class MusicService extends Service {

    MediaPlayer mediaPlayer;

    //mediaPlayer onstop之后oncreate 重新调用
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("MusicService", "onCreate");
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.beauty);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("MusicService", "IBinder");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("MusicService", "onStartCommand(Intent intent, int flags, int startId)");
        if (intent == null) {
            return super.onStartCommand(intent, flags, startId);
        }
        switch (intent.getIntExtra(MainActivity.FLAG, -1)) {
            case MainActivity.START:
                start();
                break;
            case MainActivity.PAUSE:
                pause();
                break;
            case MainActivity.STOP:
                stop();
                break;
            default:
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void stop() {
        mediaPlayer.stop();
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void pause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    private void start() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("MusicService", "onDestroy");
        if (mediaPlayer != null) {

            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
