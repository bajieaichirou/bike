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
import java.util.List;

public class PoiAdapter extends BaseAdapter {

    private List<LocationBean> datas = new ArrayList<>();
    private Context mContext;

    public PoiAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas == null ? null : datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.app_list_item_poi, null);
            vh.tv_title = (TextView) convertView.findViewById(R.id.address);
            vh.tv_text = (TextView) convertView.findViewById(R.id.addressDesc);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        LocationBean bean = (LocationBean) getItem(position);
        vh.tv_title.setText(bean.getTitle());
        vh.tv_text.setText(bean.getContent());
        return convertView;
    }

    private class ViewHolder {
        public TextView tv_title;
        public TextView tv_text;
    }

    public void setData(List<LocationBean> datas) {
        this.datas = datas;
    }

    public List<LocationBean> getDatas() {
        return datas;
    }
}