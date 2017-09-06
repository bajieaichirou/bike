package com.qianying.bike.slidingMenu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianying.bike.R;
import com.qianying.bike.adapter.CommAdapter;
import com.qianying.bike.base.BaseActivity;

import java.util.List;

/**
 * 我的消息
 * Created by Vinsen on 17/5/18.
 */

public class MineMessageActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_mine_message);

        initView();
    }

    private void initView() {
        final ImageView backIcon = (ImageView) findViewById(R.id.comm_back_arrow);
        backIcon.setOnClickListener(this);

        final TextView titleTv = (TextView) findViewById(R.id.comm_title);
        titleTv.setText(mContext.getResources().getString(R.string.mine_message));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comm_back_arrow:
                finish();
                break;
        }
    }








    /****************消息列表的数据模型*******************************************************/
    class MessageBean{

    }

    /*************************消息列表的适配器********************************************/

    class MessageAdapter extends CommAdapter<MessageBean> {

        public MessageAdapter(Context mCtx, List<MessageBean> mData, int layoutRes) {
            super(mCtx, mData, layoutRes);
        }

        @Override
        public void setItemView(ViewHolder holder, int position, View convertView, ViewGroup parent) {

        }
    }

}
