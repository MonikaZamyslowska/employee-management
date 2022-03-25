package wsb.employeemanagement.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wsb.employeemanagement.project.domain.dto.ProjectDto;
import wsb.employeemanagement.project.mapper.ProjectMapper;
import wsb.employeemanagement.project.service.ProjectService;

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
    public ProjectDto createProject(@RequestBody final ProjectDto projectDto) {
        return projectMapper.mapProjectToDto(projectService.saveProject(projectMapper.mapDtoToProject(projectDto)));
    }

    @GetMapping
    public List<ProjectDto> getProjects() {
        return projectMapper.mapProjectListToDto(projectService.getAllProjects());
    }

    @GetMapping(value = "{projectId}")
    public ProjectDto getProjectById(@PathVariable long projectId) {
        return projectMapper.mapProjectToDto(projectService.getProjectById(projectId));
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public ProjectDto updateProject(@RequestBody final ProjectDto projectDto) {
        return projectMapper.mapProjectToDto(projectService.saveProject(projectMapper.mapDtoToProject(projectDto)));
    }

    @DeleteMapping(value = "{projectId}")
    public void deleteProject(@PathVariable long projectId) {
        projectService.deleteProject(projectId);
    }
}
