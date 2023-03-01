package com.example.shop.service;

import com.example.shop.entity.User;
import com.example.shop.entity.enumiration.UserRole;
import com.example.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.shop.entity.enumiration.UserRole.ADMIN;
import static com.example.shop.entity.enumiration.UserRole.MODERATOR;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public boolean userAdminOrModeratorRole() {
        User user = getUser();
        return user.getRole().equals(ADMIN) || user.getRole().equals(MODERATOR);
    }

    public User getUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return userRepository.findByLogin(authentication.getName());
    }

    public void userSave(String name, String surname, String login, String password, String email, Long phone) {
        List<User> findUser = userRepository.findByNameAndSurname(name, surname);
        User user = new User();

        if (findUser.size() == 0) {
            user.setName(name);
            user.setSurname(surname);
            user.setLogin(login);
            user.setPassword(password);
            user.setEmail(email);
            user.setPhoneNumber(phone);
            user.setDateOfReg(LocalDateTime.now());
            user.setRole(UserRole.USER);
            userRepository.save(user);
        }
    }
}
