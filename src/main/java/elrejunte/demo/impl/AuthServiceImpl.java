package elrejunte.demo.impl;

import elrejunte.demo.entity.User;
import elrejunte.demo.repository.UserRepository;
import elrejunte.demo.service.AuthService;
import elrejunte.demo.util.Sha256Util;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return false;
        }
        user.setPassword(Sha256Util.hash(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public User login(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(u -> u.getPassword().equals(Sha256Util.hash(password)))
                .orElse(null);
    }
}