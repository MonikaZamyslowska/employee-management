package wsb.employeemanagement.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.employee.repository.EmployeeRepository;
import wsb.employeemanagement.exception.ProjectNotFoundException;
import wsb.employeemanagement.project.domain.Project;
import wsb.employeemanagement.project.repository.ProjectRepository;
import wsb.employeemanagement.task.domain.OpenCloseStatus;
import wsb.employeemanagement.task.domain.Task;
import wsb.employeemanagement.task.domain.TaskRequest;
import wsb.employeemanagement.task.domain.TaskRequestStatus;
import wsb.employeemanagement.task.repository.TaskRepository;
import wsb.employeemanagement.task.repository.TaskRequestRepository;

import java.util.List;

@Service
public class ProjectService {
    private ProjectRepository projectRepository;
    private EmployeeRepository employeeRepository;
    private TaskRepository taskRepository;
    private TaskRequestRepository taskRequestRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, EmployeeRepository employeeRepository, TaskRepository taskRepository, TaskRequestRepository taskRequestRepository) {
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
        this.taskRequestRepository = taskRequestRepository;
    }

    @Transactional
    public Project saveProject(Project project) {
        Employee employee = employeeRepository.findEmployeeByUsername(project.getOwner().getUsername());
        project.setOwner(employee);
        return projectRepository.save(project);
    }

    @Transactional
    public Project closeProject(Project project) {
        Employee employee = employeeRepository.findEmployeeByUsername(project.getOwner().getUsername());
        project.setOwner(employee);
        List<Task> taskList = taskRepository.findTasksByProject(project);
        for (Task task : taskList) {
            List<TaskRequest> taskRequests = task.getTaskRequests();
            task.setTaskStatus(OpenCloseStatus.CLOSED);
            taskRepository.save(task);
            for (TaskRequest taskRequest : taskRequests) {
                taskRequest.setTaskRequestStatus(TaskRequestStatus.REJECTED);
                taskRequestRepository.save(taskRequest);
            }
        }
        return projectRepository.save(project);
    }


    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(ProjectNotFoundException::new);
    }
}
