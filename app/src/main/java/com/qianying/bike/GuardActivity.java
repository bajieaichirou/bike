package com.qianying.bike;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.comm.H;
import com.qianying.bike.model.AuthInfo;
import com.qianying.bike.model.NetEntity;
import com.qianying.bike.model.RegInfo;
import com.qianying.bike.model.TokenInfo;
import com.qianying.bike.util.MD5Util;
import com.qianying.bike.util.PreUtils;
import com.qianying.bike.xutils3.MyCallBack;
import com.qianying.bike.xutils3.X;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuardActivity extends BaseActivity {

    private List<ImageView> datas = new ArrayList<>();
    private AuthInfo authInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard);
        final ViewPager pager = (ViewPager) findViewById(R.id.guard_vp);

        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(R.mipmap.page1);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        datas.add(imageView);


        imageView = new ImageView(mContext);
        imageView.setImageResource(R.mipmap.page2);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        datas.add(imageView);

        imageView = new ImageView(mContext);
        imageView.setImageResource(R.mipmap.page3);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        datas.add(imageView);

        imageView = new ImageView(mContext);
        imageView.setImageResource(R.mipmap.page4);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        datas.add(imageView);

        GuardVpAdapter adapter = new GuardVpAdapter();

        adapter.setDatas(datas);
        pager.setAdapter(adapter);


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == (datas.size() - 1)) {
                    TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    //Get IMEI Number of Phone
                    String imei = tm.getDeviceId();
                    Log.i("___________+++___", imei + "");
                    getRegInfo(imei);

                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        PreUtils.putBool(PreUtils.IS_FIRST_LOGIN, false);

    }
    RegInfo regInfo;
 /*
     * 获取register接口
     */

    private void getRegInfo(String imei) {
        String mdImei = MD5Util.encrypt(imei);

        Map<String, Object> map = new HashMap<>();
        map.put("imei", imei + "");
        map.put("code", mdImei);

        X.Post(H.HOST + H.authed, map, new MyCallBack<String>() {


            @Override
            protected void onFailure(String message) {
                Log.i("_______++", message + "");
            }

            @Override
            public void onSuccess(NetEntity entity) {
                Log.i("________", entity.getStatus() + "__________" + entity.getErrno() + "");
                regInfo = entity.toObj(RegInfo.class);
                RegInfo.setRegInfo(regInfo);//保存对象


                getAuthInfo();

            }
        });
    }


    /*获取授权接口*/
    private void getAuthInfo() {
        Map<String, Object> map = new HashMap<>();
        String client_id = regInfo.getApp_key();
        String state = regInfo.getSeed_secret();
        String url = regInfo.getAuthorize_url();
        map.put("client_id", client_id);
        map.put("state", state);
        map.put("response_type", "code");
        X.Post(url, map, new MyCallBack<String>() {
            @Override
            protected void onFailure(String message) {
                Log.i("_______++", message + "");
            }

            @Override
            public void onSuccess(NetEntity entity) {
                Log.i("________++___",entity.getStatus());
                authInfo = entity.toObj(AuthInfo.class);
                AuthInfo.setAuthInfo(authInfo);
                 getTokenInfo();
            }
        });

    }



    //获取Access_token

    private void getTokenInfo(){
        Map<String, Object> map = new HashMap<>();
        String url = regInfo.getToken_url();
        String client_id = regInfo.getApp_key();
        String client_secret = regInfo.getApp_secret();
        String grant_type = "authorization_code";
        String code = authInfo.getAuthorize_code();
        String state = regInfo.getSeed_secret();
        map.put("client_id",client_id);
        map.put("grant_type",grant_type);
        map.put("client_secret",client_secret);
        map.put("code",code);
        map.put("state",state);

        X.Post(url, map, new MyCallBack<String>() {
            @Override
            protected void onFailure(String message) {
                Log.i("________**",message+"");
            }

            @Override
            public void onSuccess(NetEntity entity) {
                Log.i("______!!",entity.getStatus());
                TokenInfo tokenInfo = entity.toObj(TokenInfo.class);
                TokenInfo.setTokenInfo(tokenInfo);
                startActivity(new Intent(GuardActivity.this, MainActivity.class));
                finish();

            }
        });
    }

    class GuardVpAdapter extends PagerAdapter {

        private List<ImageView> data = new ArrayList<>();


        public void setDatas(List<ImageView> datas) {
            data.clear();
            this.data = datas;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(data.get(position));//删除页卡
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
            container.addView(data.get(position), 0);//添加页卡
            return data.get(position);
        }

    }
}
