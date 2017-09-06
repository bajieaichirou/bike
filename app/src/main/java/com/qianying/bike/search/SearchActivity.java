package com.qianying.bike.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianying.bike.R;
import com.qianying.bike.adapter.AddressHistoryAdapter;
import com.qianying.bike.adapter.PoiAdapter;
import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.model.LocationBean;
import com.qianying.bike.model.MapLocation;
import com.qianying.bike.util.CacheUtil;
import com.qianying.bike.util.LocationHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索
 * Created by Vinsen on 17/5/18.
 */

public class SearchActivity extends BaseActivity implements View.OnClickListener, TextWatcher, AdapterView.OnItemClickListener {

    public static final int CHOOSE_ADDRESS_ONE = 101;
    public static final int CHOOSE_ADDRESS_TWO = 102;

    private ListView mListView;
    private EditText searchEt;
    private TextView cancelTv;
    private PoiAdapter mPoiAdapter;
    private AddressHistoryAdapter mHistoryAdapter;
    private MapLocation mMapLocation;
    private TextView myLocation;
    private RelativeLayout addressOneRl;
    private RelativeLayout addressTwoRl;
    private ImageView addressOneImg;
    private ImageView addressTwoImg;
    private TextView addressOne;
    private TextView addressTwo;
    private TextView addressTitle1;
    private TextView addressTitle2;
    private LocationBean mLocationBean1;
    private LocationBean mLocationBean2;
    private ArrayList<LocationBean> history = new ArrayList<>();
    private View clearHistory;

    public static void start(Activity context, int requestCode) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_search);
        initView();
        initCache();
    }

    private void initCache() {
        mLocationBean1 = CacheUtil.loadObject(mContext, CacheUtil.ADDRESS_ONE);
        if (mLocationBean1 != null) {
            addressOne.setText(mLocationBean1.getContent());
            addressOneImg.setImageResource(R.mipmap.used_after);
            addressTitle1.setText(mLocationBean1.getTitle());

        } else {
            addressOneImg.setImageResource(R.mipmap.used);
        }
        mLocationBean2 = CacheUtil.loadObject(mContext, CacheUtil.ADDRESS_TWO);
        if (mLocationBean2 != null) {
            addressTwo.setText(mLocationBean2.getContent());
            addressTwoImg.setImageResource(R.mipmap.used_after);
            addressTitle2.setText(mLocationBean2.getTitle());
        } else {
            addressTwoImg.setImageResource(R.mipmap.used);
        }
        List<LocationBean> histories = CacheUtil.loadList(mContext, CacheUtil.ADDRESS_HISTORY);
        history.clear();
        if (histories != null) {
            history.addAll(histories);
            if (history.size() > 0) {
                mListView.addFooterView(clearHistory);
            }
        }
    }

    private void initView() {
        searchEt = (EditText) findViewById(R.id.search_et);
        cancelTv = (TextView) findViewById(R.id.cancel_tv);
        mListView = (ListView) findViewById(R.id.list_view);
        myLocation = (TextView) findViewById(R.id.txt_location);
        addressOne = (TextView) findViewById(R.id.txt_address_one);
        addressTwo = (TextView) findViewById(R.id.txt_address_two);
        addressOneRl = (RelativeLayout) findViewById(R.id.rl_address_one);
        addressTwoRl = (RelativeLayout) findViewById(R.id.rl_address_two);
        addressOneImg = (ImageView) findViewById(R.id.img1);
        addressTwoImg = (ImageView) findViewById(R.id.img2);
        addressTitle1 = (TextView) findViewById(R.id.txt_address_title1);
        addressTitle2 = (TextView) findViewById(R.id.txt_address_title2);

        clearHistory = LayoutInflater.from(mContext).inflate(R.layout.footer_address_history, null);

        clearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSearchHistory();
            }
        });
        searchEt.addTextChangedListener(this);
        cancelTv.setOnClickListener(this);
        addressOneRl.setOnClickListener(this);
        addressTwoRl.setOnClickListener(this);

        mPoiAdapter = new PoiAdapter(mContext);
        mHistoryAdapter = new AddressHistoryAdapter(history, mContext);

        mListView.setAdapter(mHistoryAdapter);
        mListView.setOnItemClickListener(this);

        mMapLocation = LocationHelper.loadMapLocation(mContext);

        if (mMapLocation != null)
            myLocation.setText(mMapLocation.getAddress());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.rl_address_one:
                if (mLocationBean1 == null) {
                    CommonAddressActivity.start(this, CHOOSE_ADDRESS_ONE);
                } else {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("location", mLocationBean1);
                    intent.putExtra("location", bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
            case R.id.rl_address_two:
                if (mLocationBean2 == null) {
                    CommonAddressActivity.start(this, CHOOSE_ADDRESS_TWO);
                } else {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("location", mLocationBean2);
                    intent.putExtra("location", bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            if (mListView.getFooterViewsCount() > 0) {
                mListView.removeFooterView(clearHistory);
            }
            mListView.setAdapter(mPoiAdapter);
            PoiSearchTask.getInstance(mContext).setAdapter(mPoiAdapter).onSearch(s.toString(), mMapLocation.getCity(), mMapLocation.getLatitude(), mMapLocation.getLongitude());
        } else {
            mListView.setAdapter(mHistoryAdapter);
            if (history.size() > 0) {
                mListView.addFooterView(clearHistory);
            }
            mHistoryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void clearSearchHistory() {
        history.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                CacheUtil.saveList(mContext, history, CacheUtil.ADDRESS_HISTORY);
            }
        }).start();
        if (mListView.getFooterViewsCount() > 0)
            mListView.removeFooterView(clearHistory);
        mHistoryAdapter.notifyDataSetChanged();
    }

    private void saveToHistory(LocationBean locationBean) {
        if (history.size() > 0) {
            boolean contains = false;
            for (int i = 0; i < history.size(); i++) {
                if (history.get(i).getTitle().equals(locationBean.getTitle())) {
                    history.remove(i);
                    history.add(0, locationBean);
                    contains = true;
                }
            }
            if (!contains) {
                history.add(locationBean);
            }
        } else {
            history.add(locationBean);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                CacheUtil.saveList(mContext, history, CacheUtil.ADDRESS_HISTORY);
            }
        }).start();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mListView.getAdapter() instanceof AddressHistoryAdapter) {
            LocationBean locationBean = history.get(position);
            saveToHistory(locationBean);
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("location", locationBean);
            intent.putExtra("location", bundle);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            LocationBean locationBean = mPoiAdapter.getDatas().get(position);
            saveToHistory(locationBean);
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("location", locationBean);
            intent.putExtra("location", bundle);
            setResult(RESULT_OK, intent);
            finish();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_ADDRESS_ONE && resultCode == RESULT_OK) {
            mLocationBean1 = (LocationBean) data.getBundleExtra("location").getSerializable("location");
            if (mLocationBean1 == null)
                return;
            addressTitle1.setText(mLocationBean1.getTitle());
            addressOne.setText(mLocationBean1.getContent());
            addressOneImg.setImageResource(R.mipmap.used_after);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    CacheUtil.saveObject(mContext, mLocationBean1, CacheUtil.ADDRESS_ONE);
                }
            }).start();
        } else if (requestCode == CHOOSE_ADDRESS_TWO && resultCode == RESULT_OK) {
            mLocationBean2 = (LocationBean) data.getBundleExtra("location").getSerializable("location");
            if (mLocationBean2 == null)
                return;
            addressTwo.setText(mLocationBean2.getContent());
            addressTwoImg.setImageResource(R.mipmap.used_after);
            addressTitle2.setText(mLocationBean2.getTitle());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    CacheUtil.saveObject(mContext, mLocationBean2, CacheUtil.ADDRESS_TWO);
                }
            }).start();
        }
    }
}
