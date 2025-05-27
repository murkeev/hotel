package murkeev.controller;

import murkeev.model.Hotel;
import murkeev.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) { this.hotelService = hotelService; }
    @GetMapping
    public List<Hotel> getHotelsWithParams(
            @RequestParam String cityName,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam Integer personAmount
    ) {
        return hotelService.getHotelsWithParams(cityName, startDate, endDate, personAmount);
    }
    @GetMapping("/my/")
    public ResponseEntity<?> getMyHotels(@RequestHeader("Authorization") String token) {
        return hotelService.getMyHotels(token);
    }

    @GetMapping("/my/by")
    public ResponseEntity<?> getMyHotelById(@RequestHeader("Authorization") String token, @RequestParam Integer id) {
        return hotelService.getMyHotelById(token, id);
    }

    @PostMapping
    public ResponseEntity<?> changeHotels(@RequestBody Hotel hotel, @RequestHeader("Authorization") String token) {
        return hotelService.changeHotels(token, hotel);
    }
}
