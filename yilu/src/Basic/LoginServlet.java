package Basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // 获取表单参数
        String username = req.getParameter("name");
        String password = req.getParameter("password");

        int result = userService.loginOrRegister(username, password);

               resp.setContentType("text/plain;charset=UTF-8");
        if (result == 1) {
            resp.getWriter().write("{\"code\":200,\"meg\":\"登录成功\"}");
        } else if (result == 2) {
            resp.getWriter().write("{\"code\":200,\"meg\":\"注册成功\"}");
        } else {
            resp.getWriter().write("{\"code\":404,\"meg\":\"注册失败\"}");
        }
    }

    // 支持URL拼接方式：/login?username=yilu&password=401
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (username != null && password != null) {
            doPost(req, resp);
        }
    }

    public static class UserService {
        private UserDao userDao = new UserDao();

        /**
         * @return 1-登录成功, 2-注册成功, 0-注册失败
         */
        public int loginOrRegister(String username, String password) {
            // 先检查是否存在
            User user = userDao.findByUsernameAndPassword(username, password);
            if (user != null) {
                return 1;  // 登录成功
            } else {
                // 不存在则注册
                boolean success = userDao.register(username, password);
                return success ? 2 : 0;  // 2-注册成功, 0-注册失败
            }
        }
    }
}