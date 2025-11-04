package fi.roosakivila.boulderointi.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProjectRepository extends CrudRepository<Project, Long> {
    // findby tietynkäyttäjän projektit
    List<Project> findByAppuser_UserId(Long userId);
}
