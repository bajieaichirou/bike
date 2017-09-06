package com.qianying.bike.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Aaron on 2017/7/8.
 */

public class CodeUtil {

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void isCode(Context context, String code){

        if(code.equals("0")){
            showToast(context," 请求成功");
        }
        if(code.equals("40001")){
            showToast(context," 请求参数无效或错误");
        }
        if(code.equals("40002")){
            showToast(context," 获取client信息失败");
        }
        if(code.equals("40003")){
            showToast(context," 请求参数不合法");
        }
        if(code.equals("40004")){
            showToast(context," 验证授权请求失败");
        }
        if(code.equals("40005")){
            showToast(context," 获取access_token失败");
        }
        if(code.equals("40006")){
            showToast(context," 获取资源失败");
        }
        if(code.equals("40007")){
            showToast(context," 请求资源无效");
        }


        if(code.equals("40010")){
            showToast(context," 不合法的手机号");
        }
        if(code.equals("40011")){
            showToast(context," 该手机号已注册");
        }
        if(code.equals("40012")){
            showToast(context," 注册失败");
        }
        if(code.equals("40013")){
            showToast(context," 发送短信验证码失败");
        }
        if(code.equals("40014")){
            showToast(context," 短信验证码失效或验证失败");
        }
        if(code.equals("40015")){
            showToast(context,"  用户密码错误");
        }
        if(code.equals("40016")){
            showToast(context," 账号不存在");
        }
        if(code.equals("40017")){
            showToast(context," 登录失败");
        }
        if(code.equals("40018")){
            showToast(context," 实名认证失败，身份证不合法");
        }
        if(code.equals("40019")){
            showToast(context," 实名认证失败，姓名不合法");
        }
        if(code.equals("40020")){
            showToast(context," 账户余额不足");
        }
        if(code.equals("40021")){
            showToast(context," 账户已锁定");
        }
        if(code.equals("40022")){
            showToast(context," 无信用记录");
        }
        if(code.equals("40023")){
            showToast(context," 身份证不匹配");
        }



        if(code.equals("50001")){
            showToast(context," 单车状态为可使用");
        }
        if(code.equals("50002")){
            showToast(context," 单车状态为开锁中");
        }
        if(code.equals("50003")){
            showToast(context," 单车状态为使用中");
        }
        if(code.equals("50004")){
            showToast(context," 单车状态为关锁中");
        }
        if(code.equals("50005")){
            showToast(context," 单车状态为结账中");
        }
        if(code.equals("50006")){
            showToast(context," 单车状态为车辆故障");
        }
        if(code.equals("50009")){
            showToast(context," 单车状态为禁用");
        }
        if(code.equals("50020")){
            showToast(context," 该区域未找到车辆");
        }



    }
}
