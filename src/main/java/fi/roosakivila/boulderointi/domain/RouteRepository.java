package fi.roosakivila.boulderointi.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RouteRepository extends CrudRepository<Route, Long> {
    List<Route> findByGym_GymId(Long gymId);

}
