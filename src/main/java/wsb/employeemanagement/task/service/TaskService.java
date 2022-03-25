package wsb.employeemanagement.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wsb.employeemanagement.exception.TaskNotFoundException;
import wsb.employeemanagement.task.domain.Task;
import wsb.employeemanagement.task.repository.TaskRepository;

import java.util.List;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public Task getTaskById(long taskId) {
        return taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public void deleteTask(long id) {
        taskRepository.removeById(id);
    }
}
