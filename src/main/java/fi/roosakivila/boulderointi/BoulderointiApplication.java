package fi.roosakivila.boulderointi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.roosakivila.boulderointi.domain.Gym;
import fi.roosakivila.boulderointi.domain.GymRepository;
import fi.roosakivila.boulderointi.domain.ProjectRepository;
import fi.roosakivila.boulderointi.domain.Route;
import fi.roosakivila.boulderointi.domain.RouteRepository;
import fi.roosakivila.boulderointi.domain.UserRepository;

@SpringBootApplication
public class BoulderointiApplication {

	private static final Logger log = LoggerFactory.getLogger(BoulderointiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BoulderointiApplication.class, args);
	}

	@Bean
	public CommandLineRunner boulderingDemo(RouteRepository routeRepository, ProjectRepository projectRepository,
			GymRepository gymRepository, UserRepository userRepository) {
		return (args) -> {
			log.info("save few routes");
			Gym gym1 = new Gym("KiipeilyAreena", "Helsinki");
			gymRepository.save(gym1);
			Gym gym2 = new Gym("Boulderkeskus", "Espoo");
			gymRepository.save(gym2);
			Gym gym3 = new Gym("Projekti Konepaja", "Helsinki");
			gymRepository.save(gym3);

			routeRepository.save(new Route("Korneri keltainen slab", "6C", gym1));
			routeRepository.save(new Route("Monttu sininen sloper", "7A", gym2));
			routeRepository.save(new Route("Panoraama pinkki crimp", "6B+", gym3));

			log.info("fetch all routes");
			for (Route route : routeRepository.findAll()) {
				log.info(route.toString());
			}
		};
	}

}
