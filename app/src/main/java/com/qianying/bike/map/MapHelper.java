package com.qianying.bike.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapException;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.offlinemap.OfflineMapManager;
import com.qianying.bike.MyApp;
import com.qianying.bike.R;
import com.qianying.bike.model.BikeInfo;
import com.qianying.bike.model.MapLocation;
import com.qianying.bike.model.NetEntity;
import com.qianying.bike.model.RegInfo;
import com.qianying.bike.model.TokenInfo;
import com.qianying.bike.util.LocationHelper;
import com.qianying.bike.xutils3.MyCallBack;
import com.qianying.bike.xutils3.X;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 高德地图的帮助类
 * Created by Vinsen on 17/5/16.
 */

public class MapHelper implements LocationSource,
        AMapLocationListener, OfflineMapManager.OfflineMapDownloadListener {

    private static final String TAG = MapHelper.class.getSimpleName();

    private AMap aMap;
    private MapView mapView;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    private static final int STROKE_COLOR = Color.argb(0, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(0, 92, 172, 238);


    private boolean isMoveToCenter = true;

    private SensorEventHelper mSensorHelper;


    private Context context;

    private List<Marker> markers = new ArrayList<>();
    private OfflineMapManager amapManager;

    private RegInfo regInfo;
    private TokenInfo tokenInfo;

    private String client_id;
    private String state;
    private String url;
    private String access_token;

    public void init(Context context, MapView mapView) {
        this.context = context;
        this.mapView = mapView;
        //构造OfflineMapManager对象
        amapManager = new OfflineMapManager(context, this);

        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
        mSensorHelper = new SensorEventHelper(context);
        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
        }
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        UiSettings uiSettings = aMap.getUiSettings();
        uiSettings.setZoomGesturesEnabled(true);
        aMap.setTrafficEnabled(false);
        uiSettings.setCompassEnabled(false);
        uiSettings.setZoomControlsEnabled(false);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        setupLocationStyle();
    }

    private void setupLocationStyle() {
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.drawable.my_location));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(STROKE_COLOR);
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(0);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(FILL_COLOR);
        myLocationStyle.interval(2000);

        // 将自定义的 myLocationStyle 对象添加到地图上
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
    }

    public void reLocation() {
        if (mlocationClient != null) {
            isMoveToCenter = true;
            mlocationClient.startLocation();
        }
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                LatLng location = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                mListener.onLocationChanged(aMapLocation);

                if (isMoveToCenter) {
                    //定位后添加附近的车辆  每次重新定位就会重新添加
                    getBikes(aMapLocation);
//                    getBikess(aMapLocation);
                    aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
                }
                isMoveToCenter = false;

                if (aMapLocation.getLatitude() != 0) {
                    MapLocation mapLocation = LocationHelper.loadMapLocation(context);
                    aMapLocation.getCountry();
                    mapLocation.setCountry(aMapLocation.getCountry());
                    mapLocation.setProvince(aMapLocation.getProvince());
                    mapLocation.setCityCode(aMapLocation.getCityCode());
                    mapLocation.setLatitude(aMapLocation.getLatitude());
                    mapLocation.setLongitude(aMapLocation.getLongitude());
                    mapLocation.setCity(aMapLocation.getCity());
                    mapLocation.setStreet(aMapLocation.getDistrict());
                    mapLocation.setDistrict(aMapLocation.getDistrict());
                    mapLocation.setAddress(aMapLocation.getAddress());
                    mapLocation.setPoiName(aMapLocation.getPoiName());
                    LocationHelper.saveMapLocation(context, mapLocation);

                    userOffline(aMapLocation.getCity());
                }

            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e(TAG, errText);
//                MyApp.getInstance().Toast(errText);
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(context);
            mLocationOption = new AMapLocationClientOption();
            mLocationOption.setLocationCacheEnable(true);
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }


    //aMapLocation.getLatitude(), aMapLocation.getLongitude()
    public void addMarker(double latitude, double longitude) {
        addMarker(latitude, longitude, "");
    }

    //aMapLocation.getLatitude(), aMapLocation.getLongitude()
    public void addMarker(double latitude, double longitude, String title) {
        LatLng location = new LatLng(latitude, longitude);
        Bitmap bMap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.bike);
        BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bMap);
        MarkerOptions options = new MarkerOptions();
        options.icon(des);
        options.position(location);
        if (!TextUtils.isEmpty(title)) {
            options.title(title);
        }
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        Marker marker = aMap.addMarker(options);
        markers.add(marker);
        marker.showInfoWindow();
    }


    /**
     * lat 当前位置的经纬度
     * lng
     * range  多大范围内车辆
     */
    private void getBikes(final AMapLocation aMapLocation) {

        regInfo = RegInfo.newInstance();
        tokenInfo = TokenInfo.newInstance();
        client_id = regInfo.getApp_key();
        state = regInfo.getSeed_secret();
        url = regInfo.getSource_url();
        access_token = tokenInfo.getAccess_token();

        JSONObject json = new JSONObject();
        try {
            json.put("client_id",client_id);
            json.put("state",state);
            json.put("access_token",access_token);
            json.put("action","searchBikes");
            json.put("lat",String.valueOf(aMapLocation.getLatitude()));
            json.put("lng",String.valueOf(aMapLocation.getLongitude()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        X.Post(url, json, new MyCallBack<String>() {
            @Override
            protected void onFailure(String message) {

            }

            @Override
            public void onSuccess(NetEntity entity) {
                List<BikeInfo> list_bikeInfo = entity.toList(BikeInfo.class);
                for (Marker marker : markers) {
                    marker.remove();
                }
                Message message = new Message();
                message.what=101;
                handler.sendMessage(message);

                if (list_bikeInfo != null && list_bikeInfo.size() > 0) {
                    for (BikeInfo info : list_bikeInfo) {
                        addMarker(Double.valueOf(info.getLat()), Double.valueOf(info.getLng()));
                    }
                }


            }
        });


    }




    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 101:
                    Log.i("____****","lng______");
                    break;
            }
        }
    };

    public void removeOfflineMap(String city) {
        if (amapManager != null)
            amapManager.remove(city);
    }

    private void userOffline(String cityname) {
        if (amapManager == null)
            return;

        try {
            //按照citycode下载
            // amapManager.downloadByCityCode(citycode);
            //按照cityname下载
            amapManager.downloadByCityName(cityname);
        } catch (AMapException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDownload(int i, int i1, String s) {

    }

    @Override
    public void onCheckUpdate(boolean b, String s) {

    }

    @Override
    public void onRemove(boolean b, String s, String s1) {

    }
}

