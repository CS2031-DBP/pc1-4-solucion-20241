package org.demo.pc1_demo.project.infrastructure;

import org.demo.pc1_demo.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByEndDateGreaterThanEqual(ZonedDateTime date);
}
