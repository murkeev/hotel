package murkeev.controller;

import murkeev.model.Room;
import murkeev.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<Room> roomWithParams(
            @RequestParam Integer hotelId,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam Integer personAmount
    ) {
        return this.roomService.getRoomWithParams(startDate, endDate, personAmount, hotelId);
    }

    @GetMapping("/inaccessible")
    public ResponseEntity<?> makeRoomInaccessible(@RequestHeader("Authorization") String token, @RequestParam Integer id) {
        return this.roomService.makeRoomInaccessible(token, id);
    }
}
