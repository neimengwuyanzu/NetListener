package com.example.netlistener.bean;

public class NetListenerMessage {
    private String currentNet;
    private boolean isSuccess;

    public NetListenerMessage(String currentNet, boolean ping) {
        this.currentNet = currentNet;
        this.isSuccess = ping;
    }

    public String getCurrentNet() {
        return currentNet;
    }

    public void setCurrentNet(String currentNet) {
        this.currentNet = currentNet;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
