package app.auto.runner.base.openaction;

import android.view.View;

import app.auto.runner.base.ActivityStack;
import app.auto.runner.base.RRes;
import app.auto.runner.base.action.Task;
import app.auto.runner.base.framework.Init;

/**
 * Created by admin on 2017/9/3.
 */

public class Clickactivityid extends Task {


    @Override
    public Object run(View view, Object... params) {
        try {
            Class clz = Class.forName(Init.bigContext.getPackageName()+".activity."+params[0].toString());
        ActivityStack.getInstance().findActivityByClass(clz).findViewById(RRes.get("R.id."+params[1].toString()).getAndroidValue()).performClick();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }
}
