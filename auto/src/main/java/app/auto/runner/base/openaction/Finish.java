package app.auto.runner.base.openaction;

import android.app.Activity;
import android.view.View;

import app.auto.runner.base.action.Task;

/**
 * Created by admin on 2017/9/3.
 */

public class Finish extends Task {


    @Override
    public Object run(View view, Object... params) {
        ((Activity) view.getContext()).finish();
        return null;
    }
}
