package app.auto.runner.base.utility;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/11/11.
 */
public class ToastUtil {
    public static boolean toastenable = false;
    Activity act;

    public ToastUtil(Activity act) {
        this.act = act;
    }

    public void showToast(String toast){
        if(!toastenable) return;
        Toast.makeText(act,toast,Toast.LENGTH_SHORT).show();
    }
}
