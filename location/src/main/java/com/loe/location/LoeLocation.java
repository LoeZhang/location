package com.loe.location;

import android.content.Context;
import android.os.Handler;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class LoeLocation
{
    public static LocationClient location(Context context, final OnLocationListener listener)
    {
        return location(context, false, listener);
    }

    public static LocationClient locationOnce(Context context, final OnLocationListener listener)
    {
        return location(context, true, listener);
    }

    private static LocationClient location(Context context, final boolean isOnce, final OnLocationListener listener)
    {
        final LocationClient client = new LocationClient(context);
        try
        {
            LocationClientOption mOption = new LocationClientOption();

            mOption.setOnceLocation(isOnce);

            mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy); // 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
            mOption.setCoorType("bd09ll"); // 可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
            mOption.setScanSpan(4000); // 可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
            mOption.setIsNeedAddress(true); // 可选，设置是否需要地址信息，默认不需要
            mOption.setIsNeedLocationDescribe(true); // 可选，设置是否需要地址描述
            mOption.setNeedDeviceDirect(false); // 可选，设置是否需要设备方向结果
            mOption.setLocationNotify(false); // 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
            mOption.setIgnoreKillProcess(true); // 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop
            mOption.setIsNeedLocationDescribe(true); // 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation
            mOption.setIsNeedLocationPoiList(true); // 可选，默认false，设置是否需要POI结果，可以在BDLocation
            mOption.SetIgnoreCacheException(false); // 可选，默认false，设置是否收集CRASH信息，默认收集
            mOption.setOpenGps(true); // 可选，默认false，设置是否开启Gps定位
            mOption.setIsNeedAltitude(false); // 可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用

            client.registerLocationListener(new BDAbstractLocationListener()
            {
                @Override
                public void onReceiveLocation(BDLocation bdLocation)
                {
                    if(isOnce) client.stop();
                    String address = bdLocation.getAddress().address;
                    if (address == null || address.isEmpty())
                    {
                        listener.onLocation(null);
                    }
                    else
                    {
                        listener.onLocation(bdLocation);
                    }
                }
            });

            client.setLocOption(mOption);

            client.start();
        } catch (Exception e)
        {
        }
        return client;
    }

    public interface OnLocationListener
    {
        void onLocation(BDLocation location);
    }
}