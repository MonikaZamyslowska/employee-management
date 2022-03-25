package wsb.employeemanagement.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wsb.employeemanagement.exception.UserNotFoundException;
import wsb.employeemanagement.user.domain.User;
import wsb.employeemanagement.user.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public void deleteUser(long userId) {
        userRepository.removeById(userId);
    }


}
