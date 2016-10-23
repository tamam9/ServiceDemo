package example.yusuf.myservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Yusuf on 2016/10/23.
 */

public class MyReceiver extends BroadcastReceiver {
    public static final String TAG = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w(TAG, "onReceive");
        Intent intent2 = new Intent();
        intent2.setAction("org.allin.android.musicService");
        intent2.setPackage(context.getPackageName());
        if (intent.getIntExtra(MainActivity.FLAG, -1) == MainActivity.START) {
            intent2.putExtra(MainActivity.FLAG, MainActivity.START);
            context.startService(intent2);
        } else if (intent.getIntExtra(MainActivity.FLAG, -1) == MainActivity.PAUSE) {
            intent2.putExtra(MainActivity.FLAG, MainActivity.PAUSE);
            context.startService(intent2);
        }


    }
}
