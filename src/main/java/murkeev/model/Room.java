package murkeev.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "room")
@Data
public class Room {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name="roomid", unique = false, updatable = false)
    private Integer roomId;
    @Column(name="hotelid", unique = false, updatable = false)
    private Integer hotelId;
    @Column(name="bednumbers", unique = false, updatable = false)
    private Integer bedNumbers;
    @Column(name="price", unique = false, updatable = false)
    private Integer price;

    @Column(name = "status")
    private boolean status;

    private transient List<Image> images = new ArrayList<>();
    private transient Hotel hotel;


    public Integer getId() {return id;}
    public Integer getRoomId() {return roomId;}
    public Integer getBedNumbers() {return bedNumbers;}
    public Integer getPrice() {return price;}
    public Integer getHotelId() {return hotelId;}
    public void setImages(List<Image> images) {
        this.images = images;
    }
    public List<Image> getImages() {
        return images;
    }
    public boolean getStatus() {
        return status;
    }

    public void setId(Integer id) {this.id = id;}
    public void setRoomId(Integer roomId) { this.roomId = roomId; }
    public void setHotelId(int id) { this.hotelId = id; }
    public void setBedNumbers(Integer bedNumbers) {
        this.bedNumbers = bedNumbers;
    }
    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}