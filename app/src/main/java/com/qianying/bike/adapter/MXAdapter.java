package com.qianying.bike.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.qianying.bike.R;
import com.qianying.bike.base.EntityListAdapter;
import com.qianying.bike.model.MXInfo;
import com.qianying.bike.util.ViewHolder;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by Aaron on 2017/7/9.
 */

public class MXAdapter extends EntityListAdapter<MXInfo,MXHolder> {
    public MXAdapter(Context context) {
        super(context);
    }

    public MXAdapter(Context context, List<MXInfo> list) {
        super(context, list);
    }

    @Override
    protected int getAdapterRes() {
        return R.layout.adapter_mx;
    }

    @Override
    protected MXHolder getViewHolder(View root) {
        return new MXHolder(root);
    }

    @Override
    protected void initViewHolder(MXHolder mxHolder, int position) {
        mxHolder.init(getItem(position));
    }
}

class MXHolder extends ViewHolder{

    @ViewInject(R.id.mx_content)
    private TextView mx_content;
    @ViewInject(R.id.mx_date)
    private TextView mx_data;
    @ViewInject(R.id.mx_money)
    private TextView mx_money;
    @ViewInject(R.id.mx_type)
    private TextView mx_type;

    public MXHolder(View root) {
        super(root);
    }

    public void init(MXInfo info){
        mx_content.setText(info.getNote());
        mx_data.setText(info.getChanged());
        mx_money.setText(info.getChanged());
        mx_type.setText(info.getPaidtype());
    }
}
