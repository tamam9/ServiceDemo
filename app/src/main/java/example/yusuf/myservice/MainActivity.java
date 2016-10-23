package example.yusuf.myservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    public static final int START = 1;
    public static final int PAUSE = 2;
    public static final int STOP = 3;
    public static final int SHUT_DOWN = 4;
    public static final String FLAG = "FLAG";
    private Intent intent3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start = (Button) findViewById(R.id.start);
        Button pause = (Button) findViewById(R.id.pause);
        Button stop = (Button) findViewById(R.id.stop);
        Button shutdown = (Button) findViewById(R.id.shutdown);
        Button secondAct = (Button) findViewById(R.id.second_act);
        start.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
        shutdown.setOnClickListener(this);
        secondAct.setOnClickListener(this);
        //API20之后不支持在构造方法中隐式调用service,且必须设置包名
        // 报java.lang.IllegalArgumentException: Service Intent must be explicit: Intent { act=org.allin.android.musicService }
        //下面intent构造方法内的参数是action对象的名称
//        Intent intent = new Intent("org.allin.android.musicService");
//        startService(intent);

//        Intent intent = new Intent();
//        intent.setAction("org.allin.android.musicService");//你定义的service的action
//        intent.setPackage(getPackageName());//这里你需要设置你应用的包名
//        startService(intent);

        //显示调用service
        Intent intent2 = new Intent();
        intent2.setAction("org.allin.android.musicService");//你定义的service的action
        intent3 = new Intent(getExplicitIntent(getBaseContext(), intent2));
    }


    public static Intent getExplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);
        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }
        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);
        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);
        // Set the component to be explicit
        explicitIntent.setComponent(component);
        return explicitIntent;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                intent3.putExtra(FLAG, START);
                startService(intent3);
                break;
            case R.id.pause:
                intent3.putExtra(FLAG, PAUSE);
                startService(intent3);
                break;
            case R.id.stop:
                intent3.putExtra(FLAG, STOP);
                startService(intent3);
            case R.id.shutdown:
                stopService(intent3);
                break;
            case R.id.second_act:
                startActivity(new Intent(getBaseContext(), SecondAct.class));
                finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MainActivity", "onDestroy");
    }
}
