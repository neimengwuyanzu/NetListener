package com.example.netlistener;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.netlistener.bean.NetListenerMessage;
import com.example.netlistener.util.NetworkTypeEnum;
import com.example.netlistener.util.NetworkUtils;

import org.greenrobot.eventbus.EventBus;

public class NetListenerService extends Service implements NetworkStateObserver{

    /** 绑定的客户端接口 */
    IBinder mBinder;
    /** 标识服务如果被杀死之后的行为 */
    int mStartMode;


    public NetListenerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /** 调用startService()启动服务时回调 */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 当前组件创建时注册网络观察者
        NetworkStateWatcher.getDefault(this).register(this);
        return mStartMode;
    }

    /** 服务不再有用且将要被销毁时调用 */
    @Override
    public void onDestroy() {
        // 当前组件销毁时注销网络观察
        NetworkStateWatcher.getDefault(this).unRegister(this);
        // 因为这是主界面，需要清理网络观察者
        NetworkStateWatcher.getDefault(this).stopWatch(this);
    }

    @Override
    public void onNetworkStateChanged(NetworkTypeEnum type) {
        switch (type) {
            case NETWORK_4G:
//                Toast.makeText(this, "NetworkStateChanged>>>4G", Toast.LENGTH_SHORT).show();
//                tvCurrentNet.setText("当前网络为：移动网络");
                EventBus.getDefault().post(new NetListenerMessage("移动网络",NetworkUtils.ping("110.242.68.4")));
                break;

            case NETWORK_WIFI:
//                Toast.makeText(this, "NetworkStateChanged>>>WIFI", Toast.LENGTH_SHORT).show();
//                tvCurrentNet.setText("当前网络为：WIFI");
                EventBus.getDefault().post(new NetListenerMessage("WIFI",NetworkUtils.ping("110.242.68.4")));
                break;

            case NETWORK_NO:
//                Toast.makeText(this, "NetworkStateChanged>>>无网络", Toast.LENGTH_SHORT).show();
//                tvCurrentNet.setText("当前网络为：无网络");
                EventBus.getDefault().post(new NetListenerMessage("无网络",NetworkUtils.ping("110.242.68.4")));
                break;
        }
    }



}