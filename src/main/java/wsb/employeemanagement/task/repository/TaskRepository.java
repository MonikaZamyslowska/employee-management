package wsb.employeemanagement.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.project.domain.Project;
import wsb.employeemanagement.task.domain.OpenCloseStatus;
import wsb.employeemanagement.task.domain.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Task removeById(long taskId);

    List<Task> findTasksByEmployee(Employee employee);

    List<Task> findTasksByTaskStatus(OpenCloseStatus openCloseStatus);

    List<Task> findTasksByProject(Project project);
}
