package com.qianying.bike.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianying.bike.R;
import com.qianying.bike.base.BaseActivity;
import com.qianying.bike.model.NetEntity;
import com.qianying.bike.model.RegInfo;
import com.qianying.bike.model.TokenInfo;
import com.qianying.bike.widget.CustomTitlebar;
import com.qianying.bike.xutils3.MyCallBack;
import com.qianying.bike.xutils3.X;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 实名认证
 * */
public class CertificationActivity extends BaseActivity implements View.OnClickListener {

    public static final int RESULT_CARD_HAND = 10;
    public static final int RESULT_CARD_FRONT = 11;

    private CustomTitlebar mTitlebar;
    private SimpleDraweeView imgCardByHand;
    private SimpleDraweeView imgCardFront;
    private TextView submit;
    private EditText identityCard;
    private EditText name;

    private RegInfo regInfo;
    private TokenInfo tokenInfo;

    private String client_id;
    private String state;
    private String url;
    private String access_token;

//    private EditText country;
//    private String cardHandImg = "";
//    private String cardFrontImg = "";

//    public static void startForResult(Activity context,int requestCode) {
//        Intent intent = new Intent(context, CertificationActivity.class);
//        context.startActivityForResult(intent,requestCode);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification);
        regInfo = RegInfo.newInstance();
        tokenInfo = TokenInfo.newInstance();
        initViews();
    }

    /**
     * 实名认证接口
     * */
    private void getidentityCard(){
        client_id = regInfo.getApp_key();
        state = regInfo.getSeed_secret();
        url = regInfo.getSource_url();
        access_token = tokenInfo.getAccess_token();
        JSONObject json = new JSONObject();
        try {
            json.put("client_id",client_id);
            json.put("state",state);
            json.put("access_token",access_token);
            json.put("action","verified");
            json.put("truename",name.getText().toString().trim());
            json.put("idno",identityCard.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        X.Post(url, json, new MyCallBack<String>() {
            @Override
            protected void onFailure(String message) {

            }

            @Override
            public void onSuccess(NetEntity entity) {
                Message msg = new Message();
                msg.what = 103;
                handler.sendMessage(msg);
            }
        });
    }

    private void initViews() {

        mTitlebar = (CustomTitlebar) findViewById(R.id.titlebar);
//        imgCardByHand = (SimpleDraweeView) findViewById(R.id.img_card_by_hand);
//        imgCardFront = (SimpleDraweeView) findViewById(R.id.img_card_front);
        submit = (TextView) findViewById(R.id.txt_submit);
        identityCard = (EditText) findViewById(R.id.edit_card);
        name = (EditText) findViewById(R.id.edit_name);
//        country = (EditText) findViewById(R.id.edit_country);

        mTitlebar.setTitleColor(getResources().getColor(R.color.white));
        mTitlebar.setTitleText("实名认证");
        mTitlebar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
//
//        imgCardByHand.setOnClickListener(this);
//        imgCardFront.setOnClickListener(this);
        submit.setOnClickListener(this);
        WatchChange watch = new WatchChange();
        identityCard.addTextChangedListener(watch);
        name.addTextChangedListener(watch);
//        country.addTextChangedListener(this);

        submit.setBackgroundColor(getResources().getColor(R.color.gray_btn));
        submit.setEnabled(true);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.img_card_by_hand:
//                checkPromise(this, RESULT_CARD_HAND);
//                break;
//            case R.id.img_card_front:
//                checkPromise(this, RESULT_CARD_FRONT);
//                break;
            case R.id.txt_submit:
//                UserHelper.getInstance().getUser().setRegisterStatus(UserHelper.CERTIFICATED_TO_CHARGE);
//                setResult(RESULT_OK);
                getidentityCard();

                break;
        }
    }
//
//    private void checkCanSubmit() {
//        if (Utils.isIdentityCard(identityCard) && !name.getText().toString().equals("")
//              && !cardFrontImg.equals("") && !cardHandImg.equals("")) {
//            submit.setBackgroundColor(getResources().getColor(R.color.orange));
//            submit.setEnabled(true);
//        } else {
//            submit.setBackgroundColor(getResources().getColor(R.color.gray_btn));
//            submit.setEnabled(false);
//        }
//    }
//
//    private void startCamera(int requestCode) {
//        Intent i = new Intent(
//                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(i, requestCode);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RESULT_CARD_HAND && resultCode == RESULT_OK && null != data) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//            Cursor cursor = getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//
//            imgCardByHand.setImageURI(Uri.fromFile(new File(picturePath)));
//            cardHandImg=picturePath;
//            checkCanSubmit();
//
//        } else if (requestCode == RESULT_CARD_FRONT && resultCode == RESULT_OK && null != data) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//            Cursor cursor = getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//
//            imgCardFront.setImageURI(Uri.fromFile(new File(picturePath)));
//            cardFrontImg=picturePath;
//            checkCanSubmit();
//
//        }
//
//    }
//
//
//    public void checkPromise(Activity activity, int requestCode) {
//        if (Build.VERSION.SDK_INT > 23) {
//            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                        requestCode);
//            }
//        } else {
//            startCamera(requestCode);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case RESULT_CARD_HAND:
//            case RESULT_CARD_FRONT:
//                for (int i = 0; i < permissions.length; i++) {
//                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//                        startCamera(requestCode);
//                    }
//                }
//                break;
//            default:
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//
//    }
//
//    @Override
//    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//    }
//
//    @Override
//    public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//    }
//
//    @Override
//    public void afterTextChanged(Editable s) {
//        checkCanSubmit();
//    }


    /**
     * 自定义监听EditText
     */
    class WatchChange implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(name.length()>0&&identityCard.length()>0){
                Message msg = new Message();
                msg.what=101;
                handler.sendMessage(msg);
            }else {
                Message msg = new Message();
                msg.what=102;
                handler.sendMessage(msg);
            }
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 101:
                    submit.setEnabled(true);
//                    start.setBackgroundResource(R.mipmap.reg_regbtn_enable);
                    submit.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    break;
                case 102:
                    submit.setEnabled(false);
//                    start.setBackgroundResource(R.mipmap.reg_regbtn_disable);
                    submit.setBackgroundColor(getResources().getColor(R.color.gray_btn));
                    break;
                case 103:
                    Intent intent = new Intent(CertificationActivity.this,SuccessActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };
}
