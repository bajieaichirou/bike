package com.qianying.bike.slidingMenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.qianying.bike.R;
import com.qianying.bike.adapter.TravelAdapter;
import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.widget.CustomTitlebar;

/**
 * 我的行程
 * Created by qianyingmac on 17/5/18.
 */

public class MineTravelActivity extends BaseActivity {

    private CustomTitlebar mTitlebar;
    private TextView drivePile;
    private TextView carbon;
    private TextView achievement;
    private TextView noTravel;
    private RecyclerView mRecyclerView;

    private TravelAdapter mTravelAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_mine_travel);

        initViews();
    }

    private void initViews() {
        mTitlebar = (CustomTitlebar) findViewById(R.id.titlebar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        drivePile = (TextView) findViewById(R.id.txt_drive_pile);
        carbon = (TextView) findViewById(R.id.txt_carbon);
        achievement = (TextView) findViewById(R.id.txt_achieve);
        noTravel = (TextView) findViewById(R.id.txt_no);

        mTitlebar.setTitleColor(getResources().getColor(R.color.orange));
        mTitlebar.setTitleText(getString(R.string.mine_travel));
        mTitlebar.setBackgroundColor(getResources().getColor(R.color.orange));
        mTitlebar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
