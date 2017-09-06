package com.qianying.bike.slidingMenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianying.bike.R;
import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.widget.CustomTitlebar;

/**
 * 我的优惠
 * Created by Vinsen on 17/5/18.
 */

public class MineFavourableActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEditText;
    private TextView pay;
    private LinearLayout mEmptyView;
    private RecyclerView mRecyclerView;
    private CustomTitlebar mTitlebar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_mine_favourable);

        initViews();
    }

    private void initViews() {
        mEditText = (EditText) findViewById(R.id.edit_promotion);
        pay = (TextView) findViewById(R.id.txt_pay);
        mEmptyView = (LinearLayout) findViewById(R.id.ll_empty);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mTitlebar = (CustomTitlebar) findViewById(R.id.titlebar);
        mTitlebar.setTitleColor(getResources().getColor(R.color.white));
        mTitlebar.setTitleText(getString(R.string.mine_favourable));
        mTitlebar.setBackgroundColor(getResources().getColor(R.color.orange));
        mTitlebar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_pay:

                break;
        }
    }
}
