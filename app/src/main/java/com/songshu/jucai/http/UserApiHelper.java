package com.songshu.jucai.http;

import android.renderscript.Allocation;

import com.songshu.jucai.MyApp;
import com.songshu.jucai.http.api.UserApi;

import java.util.HashMap;

/**
 * user api
 * Created by vinse on 2018/9/14.
 */

public class UserApiHelper {

    /**
     * 用户信息
     */
    public static void userInfo(MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.userInfo().enqueue(callBack);
    }


    /**
     * 用户信息
     */
    public static void userInfoFix(MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.userInfoFix().enqueue(callBack);
    }

    /**
     * 用户详细信息
     */
    public static void userDetail(MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.userDetail().enqueue(callBack);
    }



    public static void mineTeacherInfo(MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.mineTeacherInfo().enqueue(callBack);
    }


    /**
     * 保存用户信息
     */
    public static void saveUserInfo(HashMap<String, Object> params, MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.saveUserInfo(params).enqueue(callBack);
    }

    /**
     * 自阅奖励
     */
    public static void ownReadPrize(HashMap<String, Object> params, MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.ownReadPrize(params).enqueue(callBack);
    }
    /**
     * 自阅奖励
     */
    public static void ownReadDuration(HashMap<String, Object> params, MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.ownReadDuration(params).enqueue(callBack);
    }


    /**
     * 时段红包
     */
    public static void userReadRedPackage(HashMap<String, Object> params,MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.userReadRedPackage(params).enqueue(callBack);
    }


    /**
     * 绑定老师
     */
    public static void bindTeacher(HashMap<String, Object> params, MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.bindTeacher(params).enqueue(callBack);
    }


    /**
     * 收入记录
     */
    public static void incomeRecord( MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.incomeRecord().enqueue(callBack);
    }
  /**
     * 收入记录
     */
    public static void incomeRecordList(HashMap<String, Object> params, MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.incomeRecordList(params).enqueue(callBack);
    }

    /**
     * 提现记录
     */
    public static void withdrawRecord(HashMap<String, Object> params, MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.withdrawRecord(params).enqueue(callBack);
    }


    /**
     * 绑定提现方式
     */
    public static void bindWechat(HashMap<String, Object> params, MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.bindWechat(params).enqueue(callBack);
    }
  /**
     * 绑定提现方式
     */
    public static void unbindAlipay(HashMap<String, Object> params, MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.unbindAlipay(params).enqueue(callBack);
    }

    /**
     * 绑定支付宝
     */
    public static void bindAlipay(HashMap<String, Object> params, MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.bindAlipay(params).enqueue(callBack);
    }

    /**
     * 切换提现方式
     */
    public static void changeWithWay(HashMap<String, Object> params, MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.changeWithWay(params).enqueue(callBack);
    }


    /**
     * 绑定提现方式
     */
    public static void getWithdrawInfo(MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.getWithdrawInfo().enqueue(callBack);
    }

    /**
     * 收徒
     */
    public static void apprentice( MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.apprentice().enqueue(callBack);
    }

    /**
     * 收徒
     */
    public static void apprenticeLink( MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.apprenticeLink().enqueue(callBack);
    }

    /**
     * 收徒列表
     */
    public static void apprenticeList(HashMap<String, Object> params, MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.apprenticeList(params).enqueue(callBack);
    }

    /**
     * 微信登录
     */
    public static void wechatLogin(HashMap<String, Object> params, MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.wechatLogin(params).enqueue(callBack);
    }

    /**
     * 绑定手机号
     */
    public static void bindPhoneNum(HashMap<String, Object> params, MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.bindPhoneNum(params).enqueue(callBack);
    }


    /**
     * 是否登录检查
     */
    public static void userCheckSign(MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.userCheckSign().enqueue(callBack);
    }


    /**
     * 提现明细
     */
    public static void withdrawDetail( MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.withdrawDetail().enqueue(callBack);
    }   /**
     * 提现明细
     */
    public static void doWithdraw(HashMap<String, Object> params, MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.doWithdraw(params).enqueue(callBack);
    }

    /**
     * 提现明细
     */
    public static void newUserWithDetail(MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.newUserWithDetail().enqueue(callBack);
    }

    /**
     * 手机登录
     */
    public static void mobileLogin(HashMap<String, Object> params,MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.mobileLogin(params).enqueue(callBack);
    }
    /**
     * 获取用户手机号码
     */
    public static void getUserPhoneNum(MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.getUserPhoneNum().enqueue(callBack);
    }

    /**
     * 获取用户手机号码
     */
    public static void recommendRandomRedpackage(MyCallBack callBack) {
        UserApi userApi = MyApp.getApp().getRetrofit().create(UserApi.class);
        userApi.recommendRandomRedpackage().enqueue(callBack);
    }

}
