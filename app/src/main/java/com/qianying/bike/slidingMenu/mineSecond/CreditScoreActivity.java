package com.qianying.bike.slidingMenu.mineSecond;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.qianying.bike.R;
import com.qianying.bike.adapter.XYAdapter;
import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.model.NetEntity;
import com.qianying.bike.model.RegInfo;
import com.qianying.bike.model.TokenInfo;
import com.qianying.bike.model.XYInfo;
import com.qianying.bike.widget.CustomTitlebar;
import com.qianying.bike.widget.fitchart.FitChart;
import com.qianying.bike.widget.fitchart.FitChartValue;
import com.qianying.bike.xutils3.MyCallBack;
import com.qianying.bike.xutils3.X;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.shnow.pulltorefreshlib.PullToRefreshBase;
import cn.shnow.pulltorefreshlib.PullToRefreshListView;


/**
 * 个人信誉积分页面
 */
public class CreditScoreActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener2<ListView>{
    @ViewInject(R.id.xy_gz)
    private TextView xy_gz;
    @ViewInject(R.id.re_listView)
    private PullToRefreshListView re_listView;

    private RegInfo regInfo;
    private TokenInfo tokenInfo;

    private String client_id;
    private String state;
    private String url;
    private String access_token;

    private XYAdapter xyAdapter;
    private CustomTitlebar mTitlebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_score);
        x.view().inject(this);
        mTitlebar = (CustomTitlebar) findViewById(R.id.titlebar);
        mTitlebar.setTitleColor(getResources().getColor(R.color.white));
        mTitlebar.setTitleText("我的信用积分");
        mTitlebar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        regInfo = RegInfo.newInstance();
        tokenInfo = TokenInfo.newInstance();
        setProgressColor();
        getXYScore();

    }


    /**
     * 动态改变progress
     */
    public void setProgressColor() {
        final FitChart fitChart = (FitChart) findViewById(R.id.fitChart);
        fitChart.setMinValue(0f);
        fitChart.setMaxValue((float) 100);
        Resources resources = getResources();
        Collection<FitChartValue> values = new ArrayList<>();
        values.add(new FitChartValue((float) 50, resources.getColor(R.color.orange)));
        values.add(new FitChartValue((float) 35, resources.getColor(R.color.text)));

        fitChart.setValues(values);
    }

    @Event(value = {R.id.xy_gz})
    private void getEvent(View view){
        switch (view.getId()){
            case R.id.xy_gz:
                break;

        }
    }

    /**
     * 获取信用积分列表接口
     * */
    private void getXYScore(){
        client_id = regInfo.getApp_key();
        state = regInfo.getSeed_secret();
        url = regInfo.getSource_url();
        access_token = tokenInfo.getAccess_token();

        JSONObject json = new JSONObject();
        try {
            json.put("client_id",client_id);
            json.put("state",state);
            json.put("access_token",access_token);
            json.put("action","getUserIntegral");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        X.Post(url, json, new MyCallBack<String>() {
            @Override
            protected void onFailure(String message) {
                re_listView.onRefreshComplete();
            }

            @Override
            public void onSuccess(NetEntity entity) {
                re_listView.onRefreshComplete();
                List<XYInfo> xyInfoList = entity.toList(XYInfo.class);
                xyAdapter.addAll(xyInfoList);
                xyAdapter = new XYAdapter(CreditScoreActivity.this);
                re_listView.setAdapter(xyAdapter);

            }
        });
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//        getXYScore(true);
        String label = DateUtils.formatDateTime(CreditScoreActivity.this, System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

        // Update the LastUpdatedLabel
        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//        getXYScore(false);
        String label = DateUtils.formatDateTime(CreditScoreActivity.this, System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

        // Update the LastUpdatedLabel
        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
    }

}
