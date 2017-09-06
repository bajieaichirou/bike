package com.qianying.bike.useBk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianying.bike.R;
import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.http.HttpResponse;
import com.qianying.bike.http.MyHttpUtils;
import com.qianying.bike.util.C;

/**
 * 骑行中
 * 骑行结束 待支付页面
 * <p>
 * Created by VinsenZhang on 2017/5/24.
 */

public class EndUseBkActivity extends BaseActivity implements View.OnClickListener {

    private TextView confirmPay;
    private TextView title;
    private ImageView moneyTag;
    private TextView moneyOrTimeTv;
    private TextView totalMoneyTv;
    private TextView scaleCardTv;
    private TextView bottomTextTv;
    private String bikeId;

    public static void startAc(Context context, String bikeId) {
        if (context instanceof EndUseBkActivity)
            return;
        Intent intent = new Intent(context, EndUseBkActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(C.BundleKeys.BikeId, bikeId);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void startAc(Context context) {
        if (context instanceof EndUseBkActivity)
            return;
        startAc(context, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_end_use_bike);

        initView();
        initData();
    }

    private void initData() {
        bikeId = getIntent().getExtras().getString(C.BundleKeys.BikeId);
    }

    private void initView() {
        confirmPay = (TextView) findViewById(R.id.confirm_pay);
        confirmPay.setOnClickListener(this);
        title = (TextView) findViewById(R.id.title_tv);
        moneyTag = (ImageView) findViewById(R.id.money_img);
        moneyOrTimeTv = (TextView) findViewById(R.id.money_or_time_tv);
        totalMoneyTv = (TextView) findViewById(R.id.total_money_tv);
        scaleCardTv = (TextView) findViewById(R.id.scale_card_tv);
        bottomTextTv = (TextView) findViewById(R.id.bottom_text_tv);

        final TextView commTitle = (TextView) findViewById(R.id.comm_title);
        commTitle.setText("骑行中");
        final ImageView commBackArrow = (ImageView) findViewById(R.id.comm_back_arrow);
        commBackArrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.comm_back_arrow:
                finish();
                break;
            case R.id.confirm_pay:
                checkout(bikeId);
                break;
        }
    }


    private void payMoney(double money) {

    }


    private void checkout(String bikeId) {
        if (TextUtils.isEmpty(bikeId))
            return;
        MyHttpUtils.put(C.Urls.getCheckOutUrl(bikeId), null, new HttpResponse() {
            @Override
            public void onGetData(String data) {
                super.onGetData(data);
            }

            @Override
            public void onError(String error) {
                super.onError(error);
            }
        });
    }

}
