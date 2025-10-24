package fi.roosakivila.boulderointi.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RouteRepository extends CrudRepository<Route, Long> {

}
