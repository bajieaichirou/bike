package com.qianying.bike.slidingMenu;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianying.bike.R;
import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.slidingMenu.ballet.BalletDetailActivity;
import com.qianying.bike.slidingMenu.ballet.DepositActivity;
import com.qianying.bike.slidingMenu.ballet.ReturnDepositActivity;
import com.qianying.bike.widget.CustomDialog;
import com.qianying.bike.widget.CustomTitlebar;

/**
 * 我的钱包页面
 * Created by Vinsen on 17/5/18.
 */

public class MineBalletActivity extends BaseActivity implements View.OnClickListener {

    private CustomTitlebar mTitlebar;
    private RelativeLayout recharge;
    private TextView balletNum;
    private TextView detail;
    private RelativeLayout depositLl;
    private TextView depositCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_mine_ballet);

        initViews();
    }

    private void initViews() {
        mTitlebar = (CustomTitlebar) findViewById(R.id.titlebar);
        recharge = (RelativeLayout) findViewById(R.id.rl_recharge);
        balletNum = (TextView) findViewById(R.id.ballet_num);
        detail = (TextView) findViewById(R.id.txt_detail);
        depositLl = (RelativeLayout) findViewById(R.id.rl_deposit);
        depositCount = (TextView) findViewById(R.id.txt_deposit);

        mTitlebar.setTitleColor(getResources().getColor(R.color.white));
        mTitlebar.setTitleText(getString(R.string.mine_wallet));
        mTitlebar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitlebar.setBackgroundColor(getResources().getColor(R.color.orange));
        detail.setOnClickListener(this);
        recharge.setOnClickListener(this);
        depositLl.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_detail://明细
                BalletDetailActivity.start(mContext);
                break;
            case R.id.rl_recharge://充值
                DepositActivity.start(mContext);
                break;
            case R.id.rl_deposit://我的押金
                final CustomDialog customDialog = new CustomDialog.Builder(mContext).setOnCLickListener(new CustomDialog.onDialogClickListener() {
                    @Override
                    public void onCancel(Dialog dialogInterface) {
                        dialogInterface.dismiss();
                    }

                    @Override
                    public void onConfirm(Dialog dialog) {
                        ReturnDepositActivity.start(mContext);
                        dialog.dismiss();

                    }
                }).created();
                customDialog.show();
                break;
        }
    }
}
