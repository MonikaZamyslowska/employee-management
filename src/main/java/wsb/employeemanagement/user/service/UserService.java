package wsb.employeemanagement.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wsb.employeemanagement.user.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;



}
