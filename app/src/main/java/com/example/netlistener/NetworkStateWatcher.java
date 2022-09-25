package com.example.netlistener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.example.netlistener.util.NetworkUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public final class NetworkStateWatcher {
    private List<WeakReference<NetworkStateObserver>> mObservers = new ArrayList<>();
    private static NetworkStateWatcher sWatcher;
    private static BroadcastReceiver sReceiver;

    public static NetworkStateWatcher getDefault(Context context) {
        if (sWatcher == null) {
            synchronized (NetworkStateWatcher.class) {
                sWatcher = new NetworkStateWatcher();
                registerReceiver(context);
            }
        }
        return sWatcher;
    }

    private static void registerReceiver(Context context) {
        if (context == null) {
            return;
        }
        sReceiver = new NetworkStateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.getApplicationContext().registerReceiver(sReceiver, filter);
    }

    /**
     * 注册观察者
     *
     * @param observer 观察者
     */
    public void register(NetworkStateObserver observer) {
        if (mObservers == null) {
            synchronized (NetworkStateWatcher.class) {
                mObservers = new ArrayList<>();
            }
        }
        mObservers.add(new WeakReference<>(observer));
    }

    /**
     * 注销观察者
     *
     * @param observer 观察者
     */
    public void unRegister(NetworkStateObserver observer) {
        if (mObservers == null || mObservers.isEmpty()) {
            return;
        }
        for (WeakReference<NetworkStateObserver> reference : mObservers) {
            if (reference.get() == observer && observer != null) {
                mObservers.remove(reference);
            }
        }
    }

    /**
     * 通知观察者网络变化
     */
    void post(Context context) {
        if (mObservers == null || mObservers.isEmpty()) {
            return;
        }
        for (WeakReference<NetworkStateObserver> observerRef : mObservers) {
            NetworkStateObserver observer = observerRef.get();
            if (observer != null) {
                observer.onNetworkStateChanged(NetworkUtils.getNetworkTypeEnum(context));
            }
        }
    }

    /**
     * 清理观察者
     *
     * @param context Android上下文
     */
    public void stopWatch(Context context) {
        if (mObservers != null) {
            mObservers.clear();
            mObservers = null;
        }
        unRegisterReceiver(context);
    }

    /**
     * 注销广播接收者
     *
     * @param context Android上下文
     */
    private void unRegisterReceiver(Context context) {
        if (context != null) {
            context.getApplicationContext().unregisterReceiver(sReceiver);
        }
    }
}
