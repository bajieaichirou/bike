package app.auto.runner.func;

import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.auto.runner.base.action.Task;

/**
 * Created by Administrator on 2017/11/2.
 */
public class Http extends Task {
    @Override
    public Object run(View view, Object... params) {
        Object url = params[0];
        String type = params[1].toString();

        List list = new ArrayList(Arrays.asList(params));
        list.remove(0);list.remove(0);

        return null;
    }
}
