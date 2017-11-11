package com.qianying.bike.customer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qianying.bike.R;

/**
 * 客服模块
 * Created by VinsenZhang on 2017/5/25.
 */

public class CustomerHelper {

    private Context mContext;
    private PopupWindow popupWindow;


    public void init(Context context) {
        this.mContext = context;
        popupWindow = new PopupWindow(mContext);
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.customer_view_layout, null);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        contentView.setBackgroundResource(R.drawable.customer);

        // 设置弹出窗体可点击
        popupWindow.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setContentView(contentView);

        popupWindow.setBackgroundDrawable(null);
        final LinearLayout suoLayout = (LinearLayout) contentView.findViewById(R.id.customer_suo_layout);
        suoLayout.setOnClickListener(customerClickListener);
        final LinearLayout guzhangLayout = (LinearLayout) contentView.findViewById(R.id.customer_guzhang_layout);
        guzhangLayout.setOnClickListener(customerClickListener);
        final LinearLayout jubaoLayout = (LinearLayout) contentView.findViewById(R.id.customer_jubao_layout);
        jubaoLayout.setOnClickListener(customerClickListener);
        final LinearLayout problemLayout = (LinearLayout) contentView.findViewById(R.id.customer_problem_layout);
        problemLayout.setOnClickListener(customerClickListener);

    }


    private final View.OnClickListener customerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String txt = ((TextView) view.findViewById(R.id.txt)).getText().toString().split("\\n")[1];
            Intent intent = new Intent();
            switch (view.getId()) {
                case R.id.customer_suo_layout:

                case R.id.customer_guzhang_layout:
                case R.id.customer_jubao_layout:
                case R.id.customer_problem_layout:
                    if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    ;//跳转到拨号界面，同时传递电话号码
                    try {
                        view.getContext().startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + txt)));
                    }catch(Exception e){

                    }
                    return;
//                    intent.setClass(mContext,UnLockProblemActivity.class);

//                    intent.setClass(mContext, ProblemBikeActivity.class);
//                    break;
//                    intent.setClass(mContext, ReportActivity.class);
//                    break;
//                    intent.setClass(mContext, OtherProblemActivity.class);
//                    break;
            }

        }
    };

    public void show(View parentView) {
        popupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 20);
    }


}
