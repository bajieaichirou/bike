package app.auto;

import android.app.Application;

import app.auto.runner.base.RRes;
import app.auto.runner.base.SPrefUtil;
import app.auto.runner.base.utility.SerialUtil;

/**
 * Created by Administrator on 2017/10/26.
 */
public class Base extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SerialUtil.initCtx(this);
        SPrefUtil.iniContext(this);

        RRes.initR(this);
    }
}
