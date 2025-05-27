package murkeev.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import murkeev.model.City;
import murkeev.repo.CityRepo;

import java.util.List;

@Service
@Transactional (rollbackOn = Exception.class)
@RequiredArgsConstructor
public class CityService {
    private final CityRepo cityRepo;

    public List<City> getCities () { return cityRepo.findAll(); }
    public City getCity (String name) {
        return cityRepo.findByCityName(name).orElseThrow(() -> new RuntimeException("City not found"));
    }
}
