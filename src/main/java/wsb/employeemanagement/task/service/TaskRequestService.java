package wsb.employeemanagement.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.employee.repository.EmployeeRepository;
import wsb.employeemanagement.exception.NotEnoughCapacityException;
import wsb.employeemanagement.exception.TaskNotFoundException;
import wsb.employeemanagement.task.domain.Task;
import wsb.employeemanagement.task.domain.TaskRequest;
import wsb.employeemanagement.task.domain.TaskRequestStatus;
import wsb.employeemanagement.task.repository.TaskRepository;
import wsb.employeemanagement.task.repository.TaskRequestRepository;

import java.util.List;

@Service
public class TaskRequestService {
    private TaskRepository taskRepository;
    private EmployeeRepository employeeRepository;
    private TaskRequestRepository taskRequestRepository;

    @Autowired
    public TaskRequestService(TaskRepository taskRepository, EmployeeRepository employeeRepository, TaskRequestRepository taskRequestRepository) {
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
        this.taskRequestRepository = taskRequestRepository;
    }

    public List<TaskRequest> getAllTaskRequestByTask(Task task) {
        return taskRequestRepository.findTaskRequestsByTask(task);
    }

    public List<TaskRequest> getAllTaskRequestByEmployee(Employee employee) {
        return taskRequestRepository.findTaskRequestsByEmployee(employee);
    }

    public TaskRequest getById(long id) {
        return taskRequestRepository.getById(id);
    }

    @Transactional
    public TaskRequest saveTaskRequest(TaskRequest taskRequest) {
        return taskRequestRepository.save(taskRequest);
    }

    @Transactional
    public TaskRequest createNewTaskRequest(TaskRequest taskRequest) {
        taskRequest.setTaskRequestStatus(TaskRequestStatus.WAITING_FOR_APPROVE);
        return taskRequestRepository.save(taskRequest);
    }

    @Transactional
    public TaskRequest acceptTaskRequest(TaskRequest taskRequest) {
        Employee employee = taskRequest.getEmployee();
        Task task = taskRequest.getTask();

        int leftCapacity = employee.getCapacity() - task.getCapacity();

        if (leftCapacity < 0) {
            throw new NotEnoughCapacityException();
        }

        taskRequest.setTaskRequestStatus(TaskRequestStatus.ACCEPTED);
        taskRequestRepository.save(taskRequest);

        employee.setCapacity(leftCapacity);
        employeeRepository.save(employee);

        taskRequestRepository.findTaskRequestsByTaskAndTaskRequestStatus(task, TaskRequestStatus.WAITING_FOR_APPROVE).stream()
                .forEach(taskRequest1 -> {
                    taskRequest1.setTaskRequestStatus(TaskRequestStatus.REJECTED);
                    taskRequestRepository.save(taskRequest1);
                });

        task.setEmployee(employee);
        taskRepository.save(task);

        return taskRequest;
    }

    public TaskRequest rejectTaskRequest(TaskRequest taskRequest) {
        taskRequest.setTaskRequestStatus(TaskRequestStatus.REJECTED);
        return taskRequestRepository.save(taskRequest);
    }
}
