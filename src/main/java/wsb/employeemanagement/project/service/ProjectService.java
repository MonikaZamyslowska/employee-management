package wsb.employeemanagement.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.employee.repository.EmployeeRepository;
import wsb.employeemanagement.exception.EmployeeNotFoundException;
import wsb.employeemanagement.exception.ProjectNotFoundException;
import wsb.employeemanagement.exception.UserNotFoundException;
import wsb.employeemanagement.project.domain.Project;
import wsb.employeemanagement.project.repository.ProjectRepository;

import java.util.List;

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
        Employee employee = employeeRepository.findEmployeeByUsername(project.getOwner().getUsername())
                .orElseThrow(EmployeeNotFoundException::new);

        project.setOwner(employee);
        return projectRepository.save(project);
    }

    @Transactional
    public void deleteProject(Long projectId) {
        Project project = getProjectById(projectId);
        projectRepository.delete(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(ProjectNotFoundException::new);
    }
}
