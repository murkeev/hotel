package murkeev.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import murkeev.config.JWTHelper;
import murkeev.model.Hotel;
import murkeev.model.Room;
import murkeev.model.Image;
import murkeev.model.City;
import murkeev.repo.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.security.InvalidParameterException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepo hotelRepo;
    private final RoomRepo roomRepo;
    private final CityRepo cityRepo;
    private final ImageRepo imageRepo;

    public ResponseEntity<?> getMyHotels(String token) {
        if (!JWTHelper.isTokenCorrect(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid");
        try {
            String email = JWTHelper.getEmailFromToken(token);
            List<Hotel> hotels = hotelRepo.findByOwnerEmail(email).orElse(new ArrayList<>());
            if (!hotels.isEmpty()) {
                for (Hotel hotel : hotels) {
                    hotel.setRoomList(roomRepo.findByHotelId(hotel.getHotelId()).orElse(new ArrayList<>()));
                    for (Room room : hotel.getRoomList()) {
                        room.setImages(imageRepo.findByRoomId(room.getId()).orElse(new ArrayList<>()));
                    }
                }
            }
            return ResponseEntity.ok().body(hotels);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Server error:(");
        }
    }

    public ResponseEntity<?> getMyHotelById(String token, int id) {
        if (!JWTHelper.isTokenCorrect(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid");
        try {
            String email = JWTHelper.getEmailFromToken(token);

            Hotel hotel = hotelRepo.findByHotelId(id).orElseThrow();
            if (!Objects.equals(hotel.getOwnerEmail(), email)) throw new InvalidParameterException();

            hotel.setRoomList(roomRepo.findByHotelId(hotel.getHotelId()).orElse(new ArrayList<>()));
            for (Room room : hotel.getRoomList()) {
                room.setImages(imageRepo.findByRoomId(room.getId()).orElse(new ArrayList<>()));
            }

            return ResponseEntity.ok().body(hotel);
        } catch (InvalidParameterException e) {
            return ResponseEntity.badRequest().body("Invalid email!");
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("No such hotel!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Server error:(");
        }
    }

    public List<Hotel> getHotelsWithParams(String cityName, String startDate, String endDate, Integer personAmount) {

        List<Hotel> hotels = hotelRepo.findAll();

        if (!cityName.isEmpty()) hotels.removeIf(hotel -> !Objects.equals(hotel.getCityName(), cityName));
        if (personAmount != 0) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                Date parsedStartDate = dateFormat.parse(startDate);
                Date parsedEndDate = dateFormat.parse(endDate);

                Timestamp timestampStart = new Timestamp(parsedStartDate.getTime());
                Timestamp timestampEnd = new Timestamp(parsedEndDate.getTime());

                List<Room> rooms = roomRepo.findByPersonAmountAndDate(personAmount, timestampStart, timestampEnd);

                hotels.removeIf(hotel -> {
                    for (Room room : rooms) {
                        if (Objects.equals(room.getHotelId(), hotel.getHotelId())) hotel.addItemToRoomList(room);
                    }
                    return hotel.getRoomListLength() <= 0;
                });

                for (Hotel hotel : hotels) {
                    for (Room room : hotel.getRoomList()) {
                        room.setImages(imageRepo.findByRoomId(room.getId()).orElse(new ArrayList<>()));
                    }
                }
            } catch (Exception e) {
                return new ArrayList<>();
            }
        }
        return hotels;
    }

    public ResponseEntity<?> changeHotels(String token, Hotel hotel) {
        try {
            if (!JWTHelper.isTokenCorrect(token))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid");
            String email = JWTHelper.getEmailFromToken(token);

            if (cityRepo.findByCityName(hotel.getCityName()).isEmpty()) cityRepo.save(new City(hotel.getCityName()));

            if (hotelRepo.findByHotelId(hotel.getHotelId()).isEmpty()) {
                hotel.setHotelId((int) hotelRepo.count() + 1);
                hotel.setOwnerEmail(email);
            }
            hotelRepo.save(hotel);

            for (Room room : hotel.getRoomList()) {
                room.setHotelId(hotel.getHotelId());
                if (roomRepo.findById(room.getId()).isEmpty()) {
                    room.setId((int) roomRepo.count() + 1);
                    room.setStatus(true);
                }
                roomRepo.save(room);

                for (Image image : room.getImages()) {
                    image.setRoomId(room.getId());
                    if (imageRepo.findByImageId(image.getImageId()).isEmpty())
                        image.setImageId((int) imageRepo.count() + 1);
                    imageRepo.save(image);
                }
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(hotel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Server error:(");
        }
    }
}
