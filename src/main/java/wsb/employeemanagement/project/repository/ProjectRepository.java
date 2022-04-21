package wsb.employeemanagement.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wsb.employeemanagement.project.domain.Project;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
