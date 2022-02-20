package wsb.employeemanagement.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wsb.employeemanagement.user.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
}
