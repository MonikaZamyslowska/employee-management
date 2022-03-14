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
    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectMapper projectMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ProjectDto createProjectDto(@RequestBody final ProjectDto projectDto) {
        return null;
    }

    @GetMapping
    public List<ProjectDto> getProjects() {
        return null;
    }

    @GetMapping(value = "/{projectId}")
    public ProjectDto getProjectById(@PathVariable long projectId) {
        return null;
    }

    @PutMapping
    public ProjectDto updateProject(@RequestBody final ProjectDto projectDto) {
        return null;
    }

    @DeleteMapping(value = "/{projectId}")
    public void deleteProject(@PathVariable long projectId) {

    }
}
