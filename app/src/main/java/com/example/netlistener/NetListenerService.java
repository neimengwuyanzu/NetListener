package com.example.netlistener;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.example.netlistener.bean.NetListenerMessage;
import com.example.netlistener.util.NetworkTypeEnum;
import com.example.netlistener.util.NetworkUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

public class NetListenerService extends Service implements NetworkStateObserver{

    /** 绑定的客户端接口 */
    IBinder mBinder;
    /** 标识服务如果被杀死之后的行为 */
    int mStartMode;
    private String currentNet;
    private Timer timer = new Timer();
    private TimerTask timerTask;
    private int pingTime = 1000;
    public NetListenerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("zyzyzy", "service   onBind");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("zyzyzy", "service   onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        Log.d("zyzyzy", "service unbindService");
    }

    /** 调用startService()启动服务时回调 */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("zyzyzy", "service   onStartCommand");
        // 当前组件创建时注册网络观察者
        NetworkStateWatcher.getDefault(this).register(this);
        NetworkUtils.getNetworkTypeEnum(this);
        timerTask = new TimerTask() {
            @Override
            public void run() {
                EventBus.getDefault().post(new NetListenerMessage(currentNet,isSuccessPing()));
                Log.d("zyzyzy", "当前网络：" + currentNet + "     是否可以链接百度 ： " + isSuccessPing());
            }
        };
        timer.schedule(timerTask,0,pingTime);
        return mStartMode;
    }

    /** 服务不再有用且将要被销毁时调用 */
    @Override
    public void onDestroy() {
        Log.d("zyzyzy", "service   onDestroy");
        timer.cancel();
        timerTask = null;
        // 当前组件销毁时注销网络观察
        NetworkStateWatcher.getDefault(this).unRegister(this);
        // 因为这是主界面，需要清理网络观察者
        NetworkStateWatcher.getDefault(this).stopWatch(this);
    }

    @Override
    public void onNetworkStateChanged(NetworkTypeEnum type) {
        switch (type) {
            case NETWORK_4G:
                currentNet = "移动网络";
                break;
            case NETWORK_WIFI:
                currentNet = "WIFI";
                break;
            case NETWORK_NO:
                currentNet = "无网络";
                break;
        }
        EventBus.getDefault().post(new NetListenerMessage(currentNet,isSuccessPing()));

    }

    private boolean isSuccessPing(){
        return NetworkUtils.ping("110.242.68.4");
    }


}