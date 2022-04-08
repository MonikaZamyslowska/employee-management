package wsb.employeemanagement.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.employee.domain.Role;
import wsb.employeemanagement.task.domain.Task;
import wsb.employeemanagement.task.domain.TaskStatus;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Task removeById(long taskId);
    List<Task> findTasksByEmployee(Employee employee);
    List<Task> findTasksByRole(Role role);
    List<Task> findTasksByTaskStatus(TaskStatus taskStatus);
}
