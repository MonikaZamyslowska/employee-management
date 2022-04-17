package wsb.employeemanagement.project.mapper;

import org.springframework.stereotype.Component;
import wsb.employeemanagement.project.domain.Project;
import wsb.employeemanagement.project.domain.dto.ProjectDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectMapper {

    public ProjectDto mapProjectToDto(Project project) {
        return new ProjectDto(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getProjectStatus(),
                project.getOwner(),
                project.getTaskList()
        );
    }

    public Project mapDtoToProject(ProjectDto projectDto) {
        return new Project(
                projectDto.getId(),
                projectDto.getName(),
                projectDto.getDescription(),
                projectDto.getProjectStatus(),
                projectDto.getOwner(),
                projectDto.getTaskList()
        );
    }

    public List<ProjectDto> mapProjectListToDto(List<Project> projects) {
        return projects.stream()
                .map(this::mapProjectToDto)
                .collect(Collectors.toList());
    }
}
