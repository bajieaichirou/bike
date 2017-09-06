package com.qianying.bike.slidingMenu.ballet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.qianying.bike.R;
import com.qianying.bike.adapter.MXAdapter;
import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.model.MXInfo;
import com.qianying.bike.model.NetEntity;
import com.qianying.bike.model.RegInfo;
import com.qianying.bike.model.TokenInfo;
import com.qianying.bike.widget.CustomTitlebar;
import com.qianying.bike.xutils3.MyCallBack;
import com.qianying.bike.xutils3.X;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import cn.shnow.pulltorefreshlib.PullToRefreshListView;

/**
 * 明细
 */
public class BalletDetailActivity extends BaseActivity {

    private CustomTitlebar mTitlebar;
//    private RecyclerView mRecyclerView;

    //    private BalletDetailAdapter mDetailAdapter;
    @ViewInject(R.id.re_listView)
    private PullToRefreshListView re_listView;
    private RegInfo regInfo;
    private TokenInfo tokenInfo;

    private String client_id;
    private String state;
    private String url;
    private String access_token;
    private MXAdapter mxAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, BalletDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ballet_detail);
        regInfo = RegInfo.newInstance();
        tokenInfo = TokenInfo.newInstance();
        initViews();
    }

    private void initViews() {
        mTitlebar = (CustomTitlebar) findViewById(R.id.titlebar);
//        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        mTitlebar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitlebar.setTitleText(getResources().getString(R.string.detail));
        mTitlebar.setTitleColor(getResources().getColor(R.color.white));
        mTitlebar.setBackgroundColor(getResources().getColor(R.color.orange));

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(linearLayoutManager);
//        mDetailAdapter = new BalletDetailAdapter(mContext);
//        mRecyclerView.setAdapter(mDetailAdapter);

        getMX();
    }

    /**
     * 获取明细接口
     */
    private void getMX() {
        client_id = regInfo.getApp_key();
        state = regInfo.getSeed_secret();
        url = regInfo.getSource_url();
        access_token = tokenInfo.getAccess_token();

        JSONObject json = new JSONObject();
        try {
            json.put("client_id", client_id);
            json.put("state", state);
            json.put("access_token", access_token);
            json.put("action", "getUserRecharge");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        X.Post(url, json, new MyCallBack<String>() {
            @Override
            protected void onFailure(String message) {

            }

            @Override
            public void onSuccess(NetEntity entity) {
                List<MXInfo> mxInfoList = entity.toList(MXInfo.class);
                mxAdapter = new MXAdapter(BalletDetailActivity.this);
                mxAdapter.addAll(mxInfoList);
                re_listView.setAdapter(mxAdapter);
            }
        });
    }
}
