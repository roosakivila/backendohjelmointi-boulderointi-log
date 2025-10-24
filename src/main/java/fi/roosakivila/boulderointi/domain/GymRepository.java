package fi.roosakivila.boulderointi.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface GymRepository extends CrudRepository<Gym, Long> {

}
