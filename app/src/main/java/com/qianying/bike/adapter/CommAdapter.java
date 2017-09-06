package com.qianying.bike.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 同用的lv  gv 适配器
 * Created by VinsenZhang on 2017/5/23.
 */

public abstract class CommAdapter<T> extends BaseAdapter {
    protected Context mCtx;
    protected List<T> mData;
    protected int layoutRes;


    public CommAdapter(Context mCtx, List<T> mData, int layoutRes) {
        this.mCtx = mCtx;
        this.mData = mData;
        this.layoutRes = layoutRes;
    }


    public void setmData(List<T> data) {
        if (mData == null) {
            mData = new ArrayList<>();
        } else {
            mData.clear();
        }


        mData.addAll(data);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }


    @Override
    public T getItem(int position) {
        return mData == null ? null : mData.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mCtx).inflate(layoutRes, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        setItemView(holder, position, convertView, parent);
        return convertView;
    }


    public abstract void setItemView(ViewHolder holder, int position, View convertView, ViewGroup parent);

    public class ViewHolder {


        private View itemView;


        public ViewHolder(View itemView) {
            this.itemView = itemView;
        }


        public <T extends View> T findById(int resId) {
            return (T) itemView.findViewById(resId);
        }
    }
}

