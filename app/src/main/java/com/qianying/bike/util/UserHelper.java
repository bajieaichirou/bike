package com.qianying.bike.util;

import android.content.Context;

import com.qianying.bike.model.User;
import com.qianying.bike.register.EnsureTelphoneActivity;
import com.qianying.bike.register.RegisterMainActivity;

/**
 * Created by fotoplace on 17/5/24.
 */

public class UserHelper {

    public static final int UN_ENSURED = -1;
    public static final int ENSURED_TO_CERTIFICATE = 0;
    public static final int CERTIFICATED_TO_CHARGE = 1;
    public static final int CHARGED_COMPLETE = 2;

    private static UserHelper mInstance;
    private Context context;
    private User user;

    private UserHelper() {
    }

    public static UserHelper getInstance() {
        if (null == mInstance) {
            mInstance = new UserHelper();
        }
        return mInstance;
    }

    public void init(Context context) {
        this.context = context.getApplicationContext();

    }

    public boolean isHaveUser() {
        return getUser().getPhone() != null && !getUser().getPhone().equals("");
    }

    public User getUser() {
        if (user == null) {
            user = PreUtils.getUser();
        }
        return user;
    }

    public void setUser(User user) {
        if (user != null && !user.getPhone().equals("")) {
            this.user = user;
            PreUtils.setUser(user);
        }
    }

    public String getAccessToken() {
        return getUser().getAccessToken();
    }

    public void setAccessToken(String accessToken) {
        getUser().setAccessToken(accessToken);
        PreUtils.setUser(getUser());
    }

    public void loginOut() {
        user = null;
        setUser(new User("", ""));
//        setAccessToken("");
        PreUtils.loginOut();
    }

    public boolean checkRegisterStatus(Context context) {
        if (getUser().getRegisterStatus() == CHARGED_COMPLETE)
            return true;
        if (getUser().getRegisterStatus() == UserHelper.UN_ENSURED) {
            EnsureTelphoneActivity.start(context);
        } else if (getUser().getRegisterStatus() == UserHelper.ENSURED_TO_CERTIFICATE) {
            RegisterMainActivity.start(context, UserHelper.ENSURED_TO_CERTIFICATE);
        } else if (getUser().getRegisterStatus() == UserHelper.CERTIFICATED_TO_CHARGE) {
            RegisterMainActivity.start(context, UserHelper.CERTIFICATED_TO_CHARGE);
        }
        return false;
    }
}
