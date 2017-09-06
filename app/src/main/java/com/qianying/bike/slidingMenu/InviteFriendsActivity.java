package com.qianying.bike.slidingMenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.qianying.bike.R;
import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.widget.CustomTitlebar;
import com.qianying.bike.widget.SharePopup;

/**
 * 邀请好友
 * Created by Vinsen on 17/5/18.
 */

public class InviteFriendsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView share;
    private CustomTitlebar mTitlebar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_invite_friends);

        initView();
    }

    private void initView() {
        share = (ImageView) findViewById(R.id.img_share);
        mTitlebar = (CustomTitlebar) findViewById(R.id.titlebar);
        mTitlebar.setTitleColor(getResources().getColor(R.color.white));
        mTitlebar.setTitleText("邀请好友");
        mTitlebar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        share.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_share:
                SharePopup sharePopup = new SharePopup(mContext);
                sharePopup.showAtLocation(findViewById(R.id.container), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
        }
    }
}
