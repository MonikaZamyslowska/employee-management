package wsb.employeemanagement.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wsb.employeemanagement.task.domain.Task;
import wsb.employeemanagement.task.domain.TaskRequest;
import wsb.employeemanagement.task.domain.TaskRequestStatus;

import java.util.List;

public interface TaskRequestRepository extends JpaRepository<TaskRequest, Long> {
    List<TaskRequest> findTaskRequestsByTaskAndTaskRequestStatus(Task task, TaskRequestStatus taskRequestStatus);

    List<TaskRequest> findTaskRequestsByTask(Task task);
}
