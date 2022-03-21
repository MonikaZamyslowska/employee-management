package wsb.employeemanagement.user.mapper;

import org.springframework.stereotype.Component;
import wsb.employeemanagement.user.domain.User;
import wsb.employeemanagement.user.domain.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDto mapUserToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword()
        );
    }

    public User mapDtoToUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getUsername(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                userDto.getPassword()
        );
    }

    public List<UserDto> mapUserListToDto(List<User> users) {
        return users.stream()
                .map(user -> mapUserToDto(user))
                .collect(Collectors.toList());
    }
}
