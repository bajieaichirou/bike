package com.qianying.bike.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qianying.bike.R;
import com.qianying.bike.model.LocationBean;

import java.util.ArrayList;

/**
 * Created by fotoplace on 2017/6/13.
 */

public class AddressHistoryAdapter extends BaseAdapter {

    private ArrayList<LocationBean> mDatas;
    private Context mContext;

    public AddressHistoryAdapter(ArrayList<LocationBean> datas, Context context) {
        mDatas = datas;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas == null ? null : mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_address_history, null);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.content = (TextView) convertView.findViewById(R.id.content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(mDatas.get(position).getTitle());
        viewHolder.content.setText(mDatas.get(position).getContent());
        return convertView;
    }

    class ViewHolder {
        public TextView title;
        public TextView content;
    }
}
