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
    private UserService userService;
    private UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public UserDto createUser(@RequestBody final UserDto userDto) {
        return userMapper.mapUserToDto(userService.saveUser(userMapper.mapDtoToUser(userDto)));
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public UserDto updateUser(@RequestBody final UserDto userDto) {
        return userMapper.mapUserToDto(userService.saveUser(userMapper.mapDtoToUser(userDto)));
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return userMapper.mapUserListToDto(userService.getAll());
    }

    @GetMapping(value = "{userId}")
    public UserDto getUserById(@PathVariable long userId) {
        return userMapper.mapUserToDto(userService.getUserById(userId));
    }

    @DeleteMapping(value = "{userId}")
    public void deleteUser(@PathVariable("userId") long userId) {
        userService.deleteUser(userId);
    }

}
