package Pro;

package com.example.service;

import com.example.dao.UserDao;
import com.example.entity.User;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    private UserDao userDao = new UserDao();

    /**
     * 登录或注册
     * @return Map包含code和msg
     */
    public Map<String, Object> loginOrRegister(String username, String password) {
        Map<String, Object> result = new HashMap<>();

        // 参数校验
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            result.put("code", 400);
            result.put("meg", "用户名和密码不能为空");
            return result;
        }

        // 先查找是否存在
        User user = userDao.findByUsernameAndPassword(username, password);
        if (user != null) {
            result.put("code", 200);
            result.put("meg", "登录成功");
            result.put("data", user);  // 附加用户信息
        } else {
            // 不存在则注册
            boolean success = userDao.register(username, password);
            if (success) {
                result.put("code", 200);
                result.put("meg", "注册成功");
            } else {
                result.put("code", 500);
                result.put("meg", "注册失败，用户名可能已存在");
            }
        }
        return result;
    }
}