package com.stt.mvpdemo.demo1.view;

import com.stt.mvpdemo.demo1.bean.User;

/**
 * @ description:
 * @ time: 2017/12/11.
 * @ author: peiyun.feng
 * @ email: fengpy@aliyun.com
 */

public interface IUserLoginView {

    String getUserName();

    String getPassword();

    void clearUserName();

    void clearPassword();

    void showLoading();

    void hideLoading();

    void toMainActivity(User user);

    void showFailedError();

}
