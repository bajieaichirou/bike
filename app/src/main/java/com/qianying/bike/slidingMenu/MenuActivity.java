package com.qianying.bike.slidingMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianying.bike.MainActivity;
import com.qianying.bike.R;
import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.http.HttpResponse;
import com.qianying.bike.model.AuthInfo;
import com.qianying.bike.model.NetEntity;
import com.qianying.bike.model.RegInfo;
import com.qianying.bike.model.TokenInfo;
import com.qianying.bike.model.User;
import com.qianying.bike.model.UserInfo;
import com.qianying.bike.model.UsersInfo;
import com.qianying.bike.register.EnsureTelphoneActivity;
import com.qianying.bike.slidingMenu.mineSecond.CreditScoreActivity;
import com.qianying.bike.slidingMenu.mineSecond.MineInfoActivity;
import com.qianying.bike.util.CommUtil;
import com.qianying.bike.util.JsonUtil;
import com.qianying.bike.util.SPUtils;
import com.qianying.bike.util.UserHelper;
import com.qianying.bike.widget.StatusViewCopy;
import com.qianying.bike.xutils3.MyCallBack;
import com.qianying.bike.xutils3.X;

import org.json.JSONException;
import org.json.JSONObject;

import app.auto.runner.base.intf.FunCallback;


/**
 * 个人中心
 * Created by Vinsen on 17/5/16.
 */

public class MenuActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout mineBalletLayout;//我的钱包
    private RelativeLayout mineFavourableLayout;//我的优惠
    private RelativeLayout mineTravelLayout;//我的行程
    private RelativeLayout mineMessageLayout;//我的消息
    private RelativeLayout inviteFriendsLayout;//邀请好友
    private RelativeLayout userBookLayout;//用户指南
    private RelativeLayout settingLayout;//设置
    private ImageView backArrow;//返回按钮
    private SimpleDraweeView userIcon;//头像图片
    private TextView userMessage;//用户信用

    private StatusViewCopy statusTel;
    private StatusViewCopy statusDeposit;
    private StatusViewCopy statusCertification;
    private StatusViewCopy statusComplete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.avtivity_menu);
        initView();
        initData();
    }

    private void initData() {
//        MyHttpUtils.getUserInfo(new HttpResponse() {
//            @Override
//            public void onGetData(String data) {
//                super.onGetData(data);
//                UserInfo userInfo = CommUtil.getBean(data, UserInfo.class);
//                User user = UserHelper.getInstance().getUser();
//                if (userInfo != null && userInfo.getData() != null && userInfo.getData().getUser() != null) {
//                    user.setUid(userInfo.getData().getUser().getId());
//                    UserHelper.getInstance().setUser(user);
//                }
////                ((TextView)findViewById(R.id.user_id)).setText(getResources().getString(R.string.app_name));
//            }
//
//            @Override
//            public void onError(String error) {
//                super.onError(error);
//            }
//
//            @Override
//            public void onErrorCode(int errorCode) {
//                super.onErrorCode(errorCode);
//            }
//        });
        UsersInfo info = UsersInfo.get(UsersInfo.class);
        if (info != null) {
            if (info.getTruename() != null && !info.getTruename().equals("")) {
                ((TextView) findViewById(R.id.user_id)).setText(info.getTruename());
            }
            if (info.getMobile() != null && !info.getMobile().equals("")) {
                ((TextView) findViewById(R.id.user_id)).setText(info.getMobile());
            }
        }
    }

    private void initView() {
//        final MyScrollView scrollView = (MyScrollView) findViewById(R.id.mine_scroll);
//        final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.title_layout);
//        frameLayout.setBackgroundResource(R.color.transparent);
//
//        final TextView titleTv = (TextView) findViewById(R.id.title_tv);
//        scrollView.setOnScrollChanged(new MyScrollView.OnScrollChanged() {
//            @Override
//            public void onScroll(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (scrollY <= 300) {
//                    float scale = (float) scrollY / 300;
//                    float alpha = (255 * scale);
//                    frameLayout.setBackgroundColor(Color.argb((int) alpha, 254, 147, 68));
//                }
//                if (scrollY > 150) {
//                    titleTv.setText("小樱单车");
//                } else {
//                    titleTv.setText("个人中心");
//                }
//            }
//        });

        statusTel = (StatusViewCopy) findViewById(R.id.status_tel);
        statusDeposit = (StatusViewCopy) findViewById(R.id.status_deposit);
        statusCertification = (StatusViewCopy) findViewById(R.id.status_certification);
        statusComplete = (StatusViewCopy) findViewById(R.id.status_complete);

        statusTel.setText(getString(R.string.title_phone_check));
        statusTel.setStatus(StatusViewCopy.COMPLETE);
        statusTel.setDivider(false);
        statusTel.setTextColor(R.color.text);
        statusDeposit.setText(getString(R.string.deposit_recharge));
        statusDeposit.setStatus(StatusViewCopy.CURRENT);
        statusDeposit.setTextColor(R.color.text);
        statusCertification.setText(getString(R.string.certification));
        statusCertification.setStatus(StatusViewCopy.NEXT);
        statusCertification.setTextColor(R.color.text);
        statusComplete.setText(getString(R.string.finish_reg));
        statusComplete.setStatus(StatusViewCopy.NEXT);
        statusComplete.setTextColor(R.color.text);
        statusComplete.setDivider(true);


        mineBalletLayout = (RelativeLayout) findViewById(R.id.mine_ballet_layout);
        mineBalletLayout.setOnClickListener(this);
        mineFavourableLayout = (RelativeLayout) findViewById(R.id.mine_favourable_layout);
        mineFavourableLayout.setOnClickListener(this);
        mineTravelLayout = (RelativeLayout) findViewById(R.id.mine_travel_layout);
        mineTravelLayout.setOnClickListener(this);
        mineMessageLayout = (RelativeLayout) findViewById(R.id.mine_message_layout);
        mineMessageLayout.setOnClickListener(this);
        inviteFriendsLayout = (RelativeLayout) findViewById(R.id.invite_friends_layout);
        inviteFriendsLayout.setOnClickListener(this);
        userBookLayout = (RelativeLayout) findViewById(R.id.user_book_layout);
        userBookLayout.setOnClickListener(this);
        settingLayout = (RelativeLayout) findViewById(R.id.setting_layout);
        settingLayout.setOnClickListener(this);
        backArrow = (ImageView) findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        userIcon = (SimpleDraweeView) findViewById(R.id.user_icon);
        userIcon.setOnClickListener(this);

        userMessage = (TextView) findViewById(R.id.user_message);
        userMessage.setOnClickListener(this);

//
//    private final View.OnClickListener backArrowListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.back_arrow:
//                    finish();
//                    break;
//                case R.id.user_icon:
//                    startActivity(new Intent(mContext, MineInfoActivity.class));
//                    break;
//                case R.id.user_id:
//        startActivity(new Intent(mContext, CreditScoreActivity.class));
//                    break;
//            }
//        }
//    };

//    private final View.OnClickListener mineItemClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent intent = new Intent();

//            startActivity(intent);
//        }
//    };

        userStatusAndOpen();
    }

    private RegInfo regInfo;//Register对象
    private AuthInfo authInfo;//Authorize对象
    private TokenInfo tokenInfo;

    private void userStatusAndOpen() {
        regInfo = RegInfo.getRegInfo();
        tokenInfo = TokenInfo.getTokenInfo();
        String client_id = regInfo.getApp_key();
        String state = regInfo.getSeed_secret();
        String url = regInfo.getSource_url();
        String access_token = tokenInfo.getAccess_token();

        JSONObject json = new JSONObject();
        try {
            json.put("client_id", client_id);
            json.put("state", state);
            json.put("access_token", access_token);
            json.put("action", "userStatus");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        X.Post(url, json, new MyCallBack<String>() {
            @Override
            protected void onFailure(String message) {
            }

            @Override
            public void onSuccess(NetEntity entity) {
//                Toast.makeText(MainActivity.this,entity.getData().toString(),Toast.LENGTH_LONG).show();
                if (entity.getStatus().equals("true") && entity.getErrno().equals("0")) {
                    UsersInfo user = JsonUtil.jsonToEntity(entity.getData().toString(), UsersInfo.class);

//                checkPromise(this);
                    int stat = 5;
                    if (user.getIs_paydeposit().equals("0")) stat = 2;
                    else if (user.getIs_verified().equals("0")) stat = 3;
                    step(stat);

                }
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.userStatusAndOpen(this, new FunCallback() {
            @Override
            public void simpleRun() {
                UsersInfo ui = UsersInfo.get(UsersInfo.class);
                ((TextView) findViewById(R.id.ballet_num)).setText(ui.getBalance());

            }
        });
    }

    public void step(int i) {

        switch (i) {
            case 1:
                statusTel.setStatus(StatusViewCopy.CURRENT);
                statusDeposit.setStatus(StatusViewCopy.NEXT);
                statusCertification.setStatus(StatusViewCopy.NEXT);
                statusComplete.setStatus(StatusViewCopy.NEXT);
                break;
            case 2:
                statusTel.setStatus(StatusViewCopy.COMPLETE);
                statusDeposit.setStatus(StatusViewCopy.CURRENT);
                statusCertification.setStatus(StatusViewCopy.NEXT);
                statusComplete.setStatus(StatusViewCopy.NEXT);
                break;
            case 3:
                statusTel.setStatus(StatusViewCopy.COMPLETE);
                statusDeposit.setStatus(StatusViewCopy.COMPLETE);
                statusCertification.setStatus(StatusViewCopy.CURRENT);
                statusComplete.setStatus(StatusViewCopy.NEXT);
                break;
            case 4:
                statusTel.setStatus(StatusViewCopy.COMPLETE);
                statusDeposit.setStatus(StatusViewCopy.COMPLETE);
                statusCertification.setStatus(StatusViewCopy.COMPLETE);
                statusComplete.setStatus(StatusViewCopy.CURRENT);

                break;
            case 5:
                statusTel.setStatus(StatusViewCopy.COMPLETE);
                statusDeposit.setStatus(StatusViewCopy.COMPLETE);
                statusCertification.setStatus(StatusViewCopy.COMPLETE);
                statusComplete.setStatus(StatusViewCopy.COMPLETE);

                break;

        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.mine_ballet_layout://我的钱包
                intent.setClass(mContext, MineBalletActivity.class);
                break;
            case R.id.mine_favourable_layout://我的收藏
                intent.setClass(mContext, MineFavourableActivity.class);
                break;
            case R.id.mine_travel_layout://我的行程
                intent.setClass(mContext, MineTravelActivity.class);
                break;
            case R.id.mine_message_layout://我的消息
                intent.setClass(mContext, MineMessageActivity.class);
                break;
            case R.id.invite_friends_layout://邀请好友
                intent.setClass(mContext, InviteFriendsActivity.class);
                break;
            case R.id.user_book_layout://用户指南
                intent.setClass(mContext, UserBookActivity.class);
                break;
            case R.id.setting_layout://设置
                intent.setClass(mContext, SettingActivity.class);
                break;
            case R.id.user_icon://跳转修改头像
                intent.setClass(mContext, MineInfoActivity.class);
                break;
            case R.id.user_message://用户信用
                intent.setClass(mContext, CreditScoreActivity.class);
                break;
        }
        startActivity(intent);
    }
}
