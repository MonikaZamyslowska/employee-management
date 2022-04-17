package wsb.employeemanagement.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.employee.repository.EmployeeRepository;
import wsb.employeemanagement.exception.ProjectNotFoundException;
import wsb.employeemanagement.project.domain.Project;
import wsb.employeemanagement.project.repository.ProjectRepository;
import wsb.employeemanagement.task.domain.Task;
import wsb.employeemanagement.task.domain.TaskRequest;
import wsb.employeemanagement.task.repository.TaskRepository;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private ProjectRepository projectRepository;
    private EmployeeRepository employeeRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, EmployeeRepository employeeRepository) {
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public Project saveProject(Project project) {
        Employee employee = employeeRepository.findEmployeeByUsername(project.getOwner().getUsername());

        project.setOwner(employee);
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
