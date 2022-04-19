package wsb.employeemanagement.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.employee.repository.EmployeeRepository;
import wsb.employeemanagement.exception.TaskNotFoundException;
import wsb.employeemanagement.task.domain.OpenCloseStatus;
import wsb.employeemanagement.task.domain.Task;
import wsb.employeemanagement.task.repository.TaskRepository;

import java.util.List;

@Service
public class TaskService {
    private TaskRepository taskRepository;
    private EmployeeRepository employeeRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public Task getTaskById(long taskId) {
        return taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Transactional
    public void deleteTask(long id) {
        taskRepository.removeById(id);
    }

    public List<Task> getAllByEmployee(Employee employee) {
        return taskRepository.findTasksByEmployee(employee);
    }

    public List<Task> getAllByStatus(OpenCloseStatus openCloseStatus) {
        return taskRepository.findTasksByTaskStatus(openCloseStatus);
    }

    @Transactional
    public Task reopenTask(Task task) {
        Employee employee = task.getEmployee();
        List<Task> employeeTasks = employee.getTasks();
        employeeTasks.remove(task);
        employee.setCapacity(employee.getCapacity() + task.getCapacity());
        employeeRepository.save(employee);

        task.setTaskStatus(OpenCloseStatus.OPEN);
        task.setEmployee(null);
        return taskRepository.save(task);
    }
}
