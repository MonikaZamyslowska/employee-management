package wsb.employeemanagement.project.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
                project.getOwner(),
                project.getTaskList()
        );
    }

    public Project mapDtoToProject(ProjectDto projectDto) {
        return new Project(
                projectDto.getId(),
                projectDto.getName(),
                projectDto.getDescription(),
                projectDto.getOwner(),
                projectDto.getTaskList()
        );
    }

    public List<ProjectDto> mapProjectListToDto(List<Project> projects) {
        return projects.stream()
                .map(project -> mapProjectToDto(project))
                .collect(Collectors.toList());
    }
}
