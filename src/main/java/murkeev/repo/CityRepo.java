package murkeev.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import murkeev.model.City;

import java.util.Optional;

@Repository
public interface CityRepo extends JpaRepository<City, String> {
    Optional<City> findByCityName(String cityName);
}
