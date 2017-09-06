package com.qianying.bike.slidingMenu.mineSecond;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianying.bike.R;
import com.qianying.bike.base.BaseActivity;

/**
 * 所有问题
 */
public class AllQuestionActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_all_question);

        initView();
    }

    private void initView() {
        final ImageView backIcon = (ImageView) findViewById(R.id.comm_back_arrow);
        backIcon.setOnClickListener(this);

        final TextView titleTv = (TextView) findViewById(R.id.comm_title);
        titleTv.setText(mContext.getResources().getString(R.string.all_question_text));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comm_back_arrow:
                finish();
                break;
        }
    }
}
