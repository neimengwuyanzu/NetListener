package com.example.netlistener;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.netlistener.bean.NetListenerMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

//public class MainActivity extends AppCompatActivity  implements NetworkStateObserver{
public class MainActivity extends AppCompatActivity {

    private TextView tvCurrentNet;
    private TextView isSuccess;
    private Intent service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvCurrentNet = findViewById(R.id.tv_current_net);
        isSuccess = findViewById(R.id.tv_is_ping_success);
        service = new Intent(this, NetListenerService.class);
//        NetListenerService service = new NetListenerService(new NetListenerCallback() {
//            @Override
//            void currentNet(String string) {
//                tvCurrentNet.setText("当前网络为：" + string);
//            }
//
//            @Override
//            void isSuccess(boolean ip) {
//                isSuccess.setText("是否ping成功" + ip);
//            }
//
//        });
//        startService(new Intent(this, NetListenerService.class));
        // 当前组件创建时注册网络观察者
//        NetworkStateWatcher.getDefault(this).register(this);
//        isSuccess.setText("是否ping成功" + NetworkUtils.ping("110.242.68.4"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("zyzyzy", "activity   onResume");
        startService(service);
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("zyzyzy", "activity   onPause");
        stopService(service);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        // 当前组件销毁时注销网络观察
//        NetworkStateWatcher.getDefault(this).unRegister(this);
        // 因为这是主界面，需要清理网络观察者
//        NetworkStateWatcher.getDefault(this).stopWatch(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(NetListenerMessage event) {
        tvCurrentNet.setText("当前网络为：" + event.getCurrentNet());
        isSuccess.setText("是否可以连接到百度：" + event.isSuccess());
    }

//    @Override
//    public void onNetworkStateChanged(NetworkTypeEnum type) {
//        switch (type) {
//            case NETWORK_4G:
////                Toast.makeText(this, "NetworkStateChanged>>>4G", Toast.LENGTH_SHORT).show();
//                tvCurrentNet.setText("当前网络为：移动网络");
//                break;
//
//            case NETWORK_WIFI:
////                Toast.makeText(this, "NetworkStateChanged>>>WIFI", Toast.LENGTH_SHORT).show();
//                tvCurrentNet.setText("当前网络为：WIFI");
//                break;
//
//            case NETWORK_NO:
////                Toast.makeText(this, "NetworkStateChanged>>>无网络", Toast.LENGTH_SHORT).show();
//                tvCurrentNet.setText("当前网络为：无网络");
//                break;
//        }
//    }
}