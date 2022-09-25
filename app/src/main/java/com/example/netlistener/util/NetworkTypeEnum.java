package com.example.netlistener.util;

public enum NetworkTypeEnum {
    NETWORK_NO(-1), NETWORK_WIFI(1), NETWORK_2G(2), NETWORK_3G(3), NETWORK_4G(4), NETWORK_UNKNOWN(5);
    public int type;

    NetworkTypeEnum(int type) {
        this.type = type;
    }
}
