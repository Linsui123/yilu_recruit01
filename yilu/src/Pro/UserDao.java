package Pro;

package com.example.dao;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.example.entity.User;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Properties;

public class UserDao {
    private static DataSource dataSource;

    static {
        try {
            Properties props = new Properties();
            props.setProperty("url", "jdbc:mysql://localhost:3306/yiludb?useSSL=false&serverTimezone=UTC");
            props.setProperty("username", "root");
            props.setProperty("password", "123456");
            props.setProperty("driverClassName", "com.mysql.cj.jdbc.Driver");
            props.setProperty("initialSize", "1");
            props.setProperty("maxActive", "10");
            dataSource = DruidDataSourceFactory.createDataSource(props);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public User findByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM User WHERE username = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUid(rs.getInt("uid"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean register(String username, String password) {
        String sql = "INSERT INTO User (username, password) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            // 用户名重复等异常
            e.printStackTrace();
        }
        return false;
    }
}
