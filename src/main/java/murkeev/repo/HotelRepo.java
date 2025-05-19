package murkeev.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import murkeev.model.Hotel;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepo extends JpaRepository<Hotel, Integer> {
    Optional<Hotel> findByHotelId(Integer hotelId);
    Optional<List<Hotel>> findByOwnerEmail(String ownerEmail);

//    @Query("SELECT ALL FROM Hotel WHERE ")
//    List<Hotel> findHotelBySearchParameters(String cityName, String startDate, String endDate, Integer personAmount);
}
