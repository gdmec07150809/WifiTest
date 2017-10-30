package com.example.administrator.wifitest;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class WifiAdmin {
    private WifiManager mWifiManager;
    private WifiInfo mWifiInfo;
    List WifiArray=null;
    String[] arraylist=null;
    private List<ScanResult> mWifiList;
    private List<WifiConfiguration> mWifiConfigurations;
    WifiLock mWifiLock;
    public WifiAdmin(Context context){
        mWifiManager=(WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        mWifiInfo=mWifiManager.getConnectionInfo();
    }
    public void openWifi(){
        if(!mWifiManager.isWifiEnabled()){
            mWifiManager.setWifiEnabled(true);
        }
    }
    public void closeWifi(){
        if(!mWifiManager.isWifiEnabled()){
            mWifiManager.setWifiEnabled(false);
        }
    }
    public int checkState() {
        return mWifiManager.getWifiState();
    }
    public void acquireWifiLock(){
        mWifiLock.acquire();
    }
    public void releaseWifiLock(){
        if(mWifiLock.isHeld()){
            mWifiLock.acquire();
        }
    }
    public void createWifiLock(){
        mWifiLock=mWifiManager.createWifiLock("test");
    }
    public List<WifiConfiguration> getConfiguration(){
        return mWifiConfigurations;
    }
    public void connetionConfiguration(int index){
        if(index>mWifiConfigurations.size()){
            return ;
        }
        mWifiManager.enableNetwork(mWifiConfigurations.get(index).networkId, true);
    }
    public void startScan(){
        mWifiManager.startScan();
        mWifiList=mWifiManager.getScanResults();
        mWifiConfigurations=mWifiManager.getConfiguredNetworks();
    }

    public List<ScanResult> getWifiList(){
        //进行排序
        WifiArray = new ArrayList(mWifiList.size());
       arraylist=new String[mWifiList.size()];
        for(int i=0;i<mWifiList.size();i++){
            arraylist[i]=mWifiList.get(i).SSID;
        }
        Arrays.sort(arraylist);
        for(int i=0;i<arraylist.length;i++){
            for(int j=0;j<mWifiList.size();j++) {
                if (mWifiList.get(j).SSID.contains(arraylist[i])) {
                    WifiArray.add(mWifiList.get(j));

                }
            }
        }
        return WifiArray;
    }
    public StringBuffer lookUpScan(){
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<mWifiList.size();i++){
            sb.append("Index_" + new Integer(i + 1).toString() + ":");
            sb.append((mWifiList.get(i)).toString()).append("\n");
        }
        return sb;
    }
    public String getMacAddress(){
        return (mWifiInfo==null)?"NULL":mWifiInfo.getMacAddress();
    }
    public String getBSSID(){
        return (mWifiInfo==null)?"NULL":mWifiInfo.getBSSID();
    }
    public int getIpAddress(){
        return (mWifiInfo==null)?0:mWifiInfo.getIpAddress();
    }
    public int getNetWordId(){
        return (mWifiInfo==null)?0:mWifiInfo.getNetworkId();
    }
    public String getWifiInfo(){
        return (mWifiInfo==null)?"NULL":mWifiInfo.toString();
    }
    public void addNetWork(WifiConfiguration configuration){
        int wcgId=mWifiManager.addNetwork(configuration);
        mWifiManager.enableNetwork(wcgId, true);
    }
    public void disConnectionWifi(int netId){
        mWifiManager.disableNetwork(netId);
        mWifiManager.disconnect();
    }
}

