package elrejunte.demo.service;


import elrejunte.demo.entity.User;

public interface AuthService {
    boolean register(User user);
    User login(String email, String password);
}