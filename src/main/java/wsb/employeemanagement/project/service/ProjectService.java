package wsb.employeemanagement.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wsb.employeemanagement.exception.ProjectNotFoundException;
import wsb.employeemanagement.project.domain.Project;
import wsb.employeemanagement.project.repository.ProjectRepository;

import java.util.List;

@Service
public class ProjectService {
    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

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
