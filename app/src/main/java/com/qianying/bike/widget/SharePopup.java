package com.qianying.bike.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.qianying.bike.R;

import java.util.HashMap;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatActionListener;
import cn.jiguang.share.android.api.Platform;
import cn.jiguang.share.android.api.ShareParams;
import cn.jiguang.share.qqmodel.QQ;
import cn.jiguang.share.qqmodel.QZone;
import cn.jiguang.share.wechat.Wechat;
import cn.jiguang.share.wechat.WechatMoments;
import cn.jiguang.share.weibo.SinaWeibo;

/**
 * Created by fotoplace on 17/6/1.
 */

public class SharePopup extends PopupWindow implements View.OnClickListener, PlatActionListener {

    private Context mContext;

    private LinearLayout wechat;
    private LinearLayout friends;
    private LinearLayout qq;
    private LinearLayout qzone;
    private LinearLayout weibo;


    public SharePopup(Context context) {
        this.mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.share_popup, null);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        setBackgroundDrawable(dw);
        setFocusable(true); // 设置PopupWindow可获得焦点
        setTouchable(true); // 设置PopupWindow可触摸
        setOutsideTouchable(true); // 设置非PopupWindow区域可触摸
        initViews(view);
    }

    private void initViews(View view) {
        wechat = (LinearLayout) view.findViewById(R.id.ll_wechat);
        friends = (LinearLayout) view.findViewById(R.id.ll_friend);
        qq = (LinearLayout) view.findViewById(R.id.ll_qq);
        qzone = (LinearLayout) view.findViewById(R.id.ll_qqzone);
        weibo = (LinearLayout) view.findViewById(R.id.ll_weibo);
        view.findViewById(R.id.container).setOnClickListener(this);

        wechat.setOnClickListener(this);
        friends.setOnClickListener(this);
        qq.setOnClickListener(this);
        qzone.setOnClickListener(this);
        weibo.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        ShareParams shareParams = new ShareParams();
        shareParams.setShareType(Platform.SHARE_TEXT);
        shareParams.setText("小樱单车");
        switch (v.getId()) {
            case R.id.ll_wechat:
                JShareInterface.share(Wechat.Name, shareParams, this);
                dismiss();

                break;
            case R.id.ll_weibo:
                JShareInterface.share(SinaWeibo.Name, shareParams, this);
                dismiss();

                break;
            case R.id.ll_friend:
                JShareInterface.share(WechatMoments.Name, shareParams, this);
                dismiss();

                break;
            case R.id.ll_qq:
                JShareInterface.share(QQ.Name, shareParams, this);
                dismiss();

                break;
            case R.id.ll_qqzone:
                JShareInterface.share(QZone.Name, shareParams, this);
                dismiss();

                break;
            case R.id.container:
                dismiss();
                break;
        }
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//        Toast.makeText(MyApp.getInstance(), "分享成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Platform platform, int i, int i1, Throwable throwable) {
//        Toast.makeText(MyApp.getInstance(), "分享失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel(Platform platform, int i) {
//        Toast.makeText(MyApp.getInstance(), "分享取消", Toast.LENGTH_SHORT).show();
    }
}
