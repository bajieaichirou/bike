package com.qianying.bike.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.qianying.bike.R;
import com.qianying.bike.adapter.PoiAdapter;
import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.model.LocationBean;
import com.qianying.bike.model.MapLocation;
import com.qianying.bike.util.LocationHelper;

/*选择常用地址*/
public class CommonAddressActivity extends BaseActivity implements TextWatcher, View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView mListView;
    private EditText searchEt;
    private TextView cancelTv;
    private PoiAdapter mPoiAdapter;
    private MapLocation mMapLocation;

    public static void start(Activity context, int request){
        Intent intent=new Intent(context,CommonAddressActivity.class);
        context.startActivityForResult(intent,request);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_address);

        init();
    }

    private void init() {
        searchEt = (EditText) findViewById(R.id.search_et);
        cancelTv = (TextView) findViewById(R.id.cancel_tv);
        mListView = (ListView) findViewById(R.id.list_view);

        searchEt.addTextChangedListener(this);
        cancelTv.setOnClickListener(this);

        mPoiAdapter = new PoiAdapter(mContext);

        mListView.setAdapter(mPoiAdapter);
        mListView.setOnItemClickListener(this);

        mMapLocation = LocationHelper.loadMapLocation(mContext);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            PoiSearchTask.getInstance(mContext).setAdapter(mPoiAdapter).onSearch(s.toString(), mMapLocation.getCity(), mMapLocation.getLatitude(), mMapLocation.getLongitude());
        } else {
            mPoiAdapter.setData(null);
            mPoiAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LocationBean locationBean = mPoiAdapter.getDatas().get(position);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("location", locationBean);
        intent.putExtra("location", bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
