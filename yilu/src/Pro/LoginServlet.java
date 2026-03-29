package Pro;

package com.example.servlet;

import com.example.service.UserService;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // 支持form表单的name参数和url拼接的username参数
        String username = req.getParameter("name");
        if (username == null) {
            username = req.getParameter("username");
        }
        String password = req.getParameter("password");

        // 调用业务层
        Map<String, Object> result = userService.loginOrRegister(username, password);

        // 返回JSON
        resp.setContentType("application/json;charset=UTF-8");
        String json = JSON.toJSONString(result);
        resp.getWriter().write(json);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 支持URL拼接方式：/login?username=yilu&password=401
        doPost(req, resp);
    }
}