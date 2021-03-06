package wsb.employeemanagement.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wsb.employeemanagement.project.domain.dto.ProjectDto;
import wsb.employeemanagement.project.mapper.ProjectMapper;
import wsb.employeemanagement.project.service.ProjectService;

import javax.annotation.security.RolesAllowed;
import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin("*")
@RequestMapping("/projects")
public class ProjectController {
    private ProjectService projectService;
    private ProjectMapper projectMapper;

    @Autowired
    public ProjectController(ProjectService projectService, ProjectMapper projectMapper) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @RolesAllowed({"ROLE_ADMIN"})
    public ProjectDto createProject(@RequestBody final ProjectDto projectDto) {
        return projectMapper.mapProjectToDto(projectService.saveProject(projectMapper.mapDtoToProject(projectDto)));
    }

    @GetMapping
    @RolesAllowed({"ROLE_EMPLOYEE", "ROLE_PM", "ROLE_ADMIN"})
    public List<ProjectDto> getProjects() {
        return projectMapper.mapProjectListToDto(projectService.getAllProjects());
    }

    @GetMapping("{projectId}")
    @RolesAllowed({"ROLE_EMPLOYEE", "ROLE_PM", "ROLE_ADMIN"})
    public ProjectDto getProjectById(@PathVariable long projectId) {
        return projectMapper.mapProjectToDto(projectService.getProjectById(projectId));
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    @RolesAllowed({"ROLE_ADMIN"})
    public ProjectDto updateProject(@RequestBody final ProjectDto projectDto) {
        return projectMapper.mapProjectToDto(projectService.saveProject(projectMapper.mapDtoToProject(projectDto)));
    }
}
