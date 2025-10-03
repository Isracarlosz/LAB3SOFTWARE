package com.demo.productmanagement.service;

import com.demo.productmanagement.model.User;
import com.demo.productmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean updateUser(User user) {
        return userRepository.update(user);
    }

    @Override
    public boolean deleteUser(Long id) {
        return userRepository.deleteById(id);
    }

    @Override
    public boolean validateLogin(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        return userOpt.isPresent() && userOpt.get().getPassword().equals(password);
    }

    // --------------------------------------------------------
    // AÑADIDO: Listar usuarios excluyendo al logueado
    // --------------------------------------------------------
    @Override
    public List<User> getAllUsersExcludingId(Long excludedId) {
        if (excludedId == null) {
            return userRepository.findAll();
        }
        return userRepository.findAllExcludingId(excludedId);
    }
}
