package example.yusuf.myservice;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Yusuf on 2016/10/23.
 */

public class MainAct2 extends AppCompatActivity implements View.OnClickListener {

    MusicService2 musicService2;
    Button bind, unbind, nextPage;
    TextView secret;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        bind = (Button) findViewById(R.id.bind);
        unbind = (Button) findViewById(R.id.unbind);
        secret = (TextView) findViewById(R.id.result);
        nextPage = (Button) findViewById(R.id.next);
        bind.setOnClickListener(this);
        unbind.setOnClickListener(this);
        nextPage.setOnClickListener(this);

    }


    ServiceConnection serviceConnection;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bind:
                if (serviceConnection != null) {
                    return;
                }
                Intent intent = new Intent(getBaseContext(), MusicService2.class);
                serviceConnection = new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        musicService2 = ((MusicService2.MBinder) service).getService();
                        secret.setText(musicService2.secret);
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        musicService2 = null;
                    }
                };
                bindService(intent, serviceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.unbind:
                unbindService(serviceConnection);
            case R.id.next:
                startActivity(new Intent(this, SecondAct.class));
                finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceConnection != null) {

            unbindService(serviceConnection);
        }
    }
}
