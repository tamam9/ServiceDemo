package example.yusuf.myservice;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Yusuf on 2016/10/23.
 */

public class SecondAct extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView start = (TextView) findViewById(R.id.start_broadcast);
        TextView pause = (TextView) findViewById(R.id.pause_broadcast);
        final Intent intent = new Intent();
        intent.setAction("org.allin.android.musicReceiver");
        intent.setPackage(getPackageName());
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(MainActivity.FLAG, MainActivity.START);
                sendBroadcast(intent);
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(MainActivity.FLAG, MainActivity.PAUSE);
                sendBroadcast(intent);
            }
        });
    }


}
