package com.qianying.bike.customer;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

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
            Intent intent = new Intent();
            switch (view.getId()) {
                case R.id.customer_suo_layout:
                    intent.setClass(mContext,UnLockProblemActivity.class);
                    break;
                case R.id.customer_guzhang_layout:
                    intent.setClass(mContext, ProblemBikeActivity.class);
                    break;
                case R.id.customer_jubao_layout:
                    intent.setClass(mContext, ReportActivity.class);
                    break;
                case R.id.customer_problem_layout:
                    intent.setClass(mContext, OtherProblemActivity.class);
                    break;
            }

            mContext.startActivity(intent);
        }
    };

    public void show(View parentView) {
        popupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 20);
    }


}
