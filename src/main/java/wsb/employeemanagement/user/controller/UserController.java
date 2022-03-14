package wsb.employeemanagement.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wsb.employeemanagement.user.domain.User;
import wsb.employeemanagement.user.domain.dto.UserDto;
import wsb.employeemanagement.user.mapper.UserMapper;
import wsb.employeemanagement.user.service.UserService;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public UserDto createUser(@RequestBody final UserDto userDto) {
        return null;
    }

    @PutMapping
    public UserDto updateUser(@RequestBody final UserDto userDto) {
        return null;
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return null;
    }

    @DeleteMapping(value = "/{userId}")
    public void deleteUser(@PathVariable("userId") long userId) {

    }

}
