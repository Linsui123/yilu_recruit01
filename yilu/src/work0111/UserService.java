package work0111;

import com.example.springbootlogin.entity.User;

public interface UserService {
    User login(String username, String password);
}