package com.qianying.bike.xutils3;

import com.qianying.bike.model.NetEntity;
import com.qianying.bike.util.JsonUtil;

import org.xutils.common.Callback;


/**
 *   @describe 回调方法
 */
public abstract class MyCacheCallBack<ResultType> implements Callback.CacheCallback<ResultType>{
//    @Override
//    public void onWaiting() {
//
//    }
//
//    @Override
//    public void onStarted() {
//    }
//
//    @Override
//    public void onLoading(long l, long l1, boolean b) {
//
//    }

    @Override
    public void onSuccess(ResultType resultType) {

        NetEntity entity = JsonUtil.jsonToEntity(resultType.toString(), NetEntity.class);
        if ("0".equals(entity.getStatus())) {
            onSuccess(entity);
        } else {

                onFailure(entity.getErrno());
            }

    }

    @Override
    public void onError(Throwable throwable, boolean b) {

    }

    @Override
    public void onCancelled(CancelledException e) {

    }

    @Override
    public void onFinished() {

    }

    protected abstract void onFailure(String message);

    public abstract void onSuccess(NetEntity entity);


}
