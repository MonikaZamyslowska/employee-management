package wsb.employeemanagement.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wsb.employeemanagement.task.domain.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
