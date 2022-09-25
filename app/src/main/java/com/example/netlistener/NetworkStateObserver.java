package com.example.netlistener;

import com.example.netlistener.util.NetworkTypeEnum;

public interface NetworkStateObserver {
    /**
     * 网络发生变化
     *
     * @param type 网络类型
     */
    void onNetworkStateChanged(NetworkTypeEnum type);
}
