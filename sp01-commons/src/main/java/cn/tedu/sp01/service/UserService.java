package cn.tedu.sp01.service;

import cn.tedu.sp01.entity.User;

public interface UserService {
    // 获取用户
    User getUser(Integer userId);

    // 增加用户积分
    void addScore(Integer userId, Integer score);
}
