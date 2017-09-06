package com.qianying.bike;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.util.PreUtils;

import java.util.ArrayList;
import java.util.List;

public class GuardActivity extends BaseActivity {

    private List<ImageView> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard);
        final ViewPager pager = (ViewPager) findViewById(R.id.guard_vp);

        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(R.mipmap.page1);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        datas.add(imageView);


        imageView = new ImageView(mContext);
        imageView.setImageResource(R.mipmap.page2);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        datas.add(imageView);

        imageView = new ImageView(mContext);
        imageView.setImageResource(R.mipmap.page3);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        datas.add(imageView);

        imageView = new ImageView(mContext);
        imageView.setImageResource(R.mipmap.page4);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        datas.add(imageView);

        GuardVpAdapter adapter = new GuardVpAdapter();

        adapter.setDatas(datas);
        pager.setAdapter(adapter);


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == (datas.size() - 1)) {
                    startActivity(new Intent(GuardActivity.this, MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        PreUtils.putBool(PreUtils.IS_FIRST_LOGIN, false);

    }


    class GuardVpAdapter extends PagerAdapter {

        private List<ImageView> data = new ArrayList<>();


        public void setDatas(List<ImageView> datas) {
            data.clear();
            this.data = datas;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(data.get(position));//删除页卡
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
            container.addView(data.get(position), 0);//添加页卡
            return data.get(position);
        }

    }
}
