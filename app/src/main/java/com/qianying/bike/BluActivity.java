package com.qianying.bike;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.omnilockbt.bt.BLEService;
import com.qianying.bike.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class BluActivity extends BaseActivity {
    private static final String TAG = "BluActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, BLEService.class);
        startService(intent);
        getBluService();
//
//        // test
//        mSend = (Button) findViewById(R.id.send);
//        mSend1 = (Button) findViewById(R.id.send1);
//        mSend2 = (Button) findViewById(R.id.send2);
//        mSend3 = (Button) findViewById(R.id.send3);
//        mSend4 = (Button) findViewById(R.id.send4);
//        mSend5 = (Button) findViewById(R.id.send5);
//        mSetBleMac = (Button) findViewById(R.id.setBleMac);
//        mSetAppId = (Button) findViewById(R.id.setAppId);
//        mSetAppId.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//
//                // TODO Auto-generated method stub
//                scan(bleDeviceMac);
//            }
//
//        });
//        mSetBleMac.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//
//            }
//
//        });
//        mSend.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//
//            }
//
//        });
//
//        mSend1.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//                Intent intent = new Intent();
//                intent.setAction(BLEService.ACTION_GET_UNLOCK_KEY);
//                sendBroadcast(intent);
//            }
//
//        });
//        mSend2.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//                Intent intent = new Intent();
//                intent.setAction(BLEService.ACTION_UNLOCK);
//                sendBroadcast(intent);
//            }
//
//        });
//        mSend3.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//                Intent intent = new Intent();
//                intent.setAction(BLEService.ACTION_QUERY);
//                sendBroadcast(intent);
//            }
//
//        });
//        mSend4.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//                Intent intent = new Intent();
//                intent.setAction(BLEService.ACTION_GET_UNUPLOAD);
//                sendBroadcast(intent);
//            }
//
//        });
//        mSend5.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//                Intent intent = new Intent();
//                intent.setAction(BLEService.ACTION_CLEAN_UNUPLOAD);
//                sendBroadcast(intent);
//            }
//
//        });
    }

    String bleDeviceMac = "FF:FF:11:00:03:A4";

    public void scan(String bleDeviceMac) {

        if (bleDeviceMac != null && !bleDeviceMac.equals("")) {
            this.bleDeviceMac = bleDeviceMac;
        }
        Intent intent = new Intent();
        intent.setAction(BLEService.ACTION_SET_APP_ID);
        byte[] appid = new byte[]{0x11, 0x11, 0x11, 0x11};
        intent.putExtra(BLEService.SET_APP_ID_DATA, appid);
        sendBroadcast(intent);
        intent = new Intent();
        intent.setAction(BLEService.ACTION_SET_LE_MAC);
        intent.putExtra(BLEService.SET_LE_MAC_DATA, this.bleDeviceMac);
        sendBroadcast(intent);
        intent = new Intent();
        intent.setAction(BLEService.START_LE_SCAN);
        sendBroadcast(intent);
    }

    public boolean detect() {
        if(!canReconnected){
            Toast.makeText(this,"蓝牙正在断开...",Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        unregister();
        Intent intent = new Intent(this, BLEService.class);
        stopService(intent);
    }

    private void unregister() {
        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver);
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (isNeedCheck) {
            checkPermissions(needPermissions);
        }
        register();



    }

    private void getBluService() {
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
    }

    BluetoothAdapter mBluetoothAdapter;
    private void register() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BLEService.RECEIVER_LOCK);
        filter.addAction(BLEService.RECEIVER_UNLOCK);
        filter.addAction(BLEService.RECEIVER_UNLOCK_KEY);
        filter.addAction(BLEService.RECEIVER_QUERY);
        filter.addAction(BLEService.RECEIVER_GET_UNUPLOAD);
        filter.addAction(BLEService.RECEIVER_CLEAN_UNUPLOAD);
        filter.addAction(BLEService.RECEIVER_CONNECT_SUCCESS);
        filter.addAction(BLEService.RECEIVER_CONNECT_FAILURE);
        filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);

        registerReceiver(mBroadcastReceiver, filter);
    }


    private boolean canReconnected = true;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "action: " + action);
            if (action.equals(BLEService.RECEIVER_LOCK)) {
                byte data = intent.getByteExtra(BLEService.RECEIVER_LOCK_RESULT, (byte) 0);
                Log.d(TAG, "hex data: " + data);
//                Intent intent1 = new Intent();
//                intent1.setAction(BLEService.ACTION_LOCK);
//                sendBroadcast(intent1);

                stopService(new Intent(context, BLEService.class));

                unregister();
                startService(new Intent(context, BLEService.class));
                register();
            } else if (action.equals(BLEService.RECEIVER_CONNECT_SUCCESS)) {
                Intent intent1 = new Intent();
                intent1.setAction(BLEService.ACTION_GET_UNLOCK_KEY);
                sendBroadcast(intent1);
            } else if (action.equals(BLEService.RECEIVER_CONNECT_FAILURE)) {
                stopService(new Intent(context, BLEService.class));

                unregister();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startService(new Intent(context, BLEService.class));
                register();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.d(TAG, "hex data: connect_failure_reconnect" );
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                scan("");
            } else if (action.equals(BLEService.RECEIVER_UNLOCK)) {
                byte unlockResult = intent.getByteExtra(BLEService.RECEIVER_UNLOCK_RESULT, (byte) 0);
                Log.d(TAG, "hex data: 2 "+unlockResult);
                unlockCallback();
            } else if (action.equals(BLEService.RECEIVER_UNLOCK_KEY)) {
                byte key = intent.getByteExtra(BLEService.RECEIVER_UNLOCK_KEY_RESULT, (byte) 0);
                Log.d(TAG, "hex data: 3 "+key);
                Intent intent1 = new Intent();
                intent1.setAction(BLEService.ACTION_UNLOCK);
                sendBroadcast(intent1);
            } else if (action.equals(BLEService.RECEIVER_QUERY)) {
                byte LockStatus = intent.getByteExtra(BLEService.RECEIVER_QUERY_LOCKSTATUS, (byte) 0);
                byte batteryCapacity = intent.getByteExtra(BLEService.RECEIVER_QUERY_BATTERYCAPACITY, (byte) 0);
                byte isUnupload = intent.getByteExtra(BLEService.RECEIVER_QUERY_ISUNUPLOAD, (byte) 0);
            } else if (action.equals(BLEService.RECEIVER_GET_UNUPLOAD)) {
                byte[] rideTime = intent.getByteArrayExtra(BLEService.RECEIVER_GET_UNUPLOAD_RIDETIME);
                byte[] userID = intent.getByteArrayExtra(BLEService.RECEIVER_GET_UNUPLOAD_USERID);
            } else if (action.equals(BLEService.RECEIVER_CLEAN_UNUPLOAD)) {
                byte cleanResult = intent.getByteExtra(BLEService.RECEIVER_CLEAN_UNUPLOAD_RESULT, (byte) 0);
            }
//            else if(action.equals()){
//
//                stopService(new Intent(context, BLEService.class));
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                startService(new Intent(context, BLEService.class));
//
//            }

        }
    };

    public void unlockCallback() {
    }

    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA


    };

    private static final int PERMISSON_REQUESTCODE = 0;

    /**
     * 判断是否需要检测，防止不停的弹框
     */
    private boolean isNeedCheck = true;


    /**
     * @param needRequestPermissonList
     * @since 2.5.0
     */
    private void checkPermissions(String... permissions) {
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    needRequestPermissonList.toArray(
                            new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }

    /**
     * 检测是否说有的权限都已经授权
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt)) {
                isNeedCheck = false;
            }
        }
    }


}
