package murkeev.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "hotel")
@Data
public class Hotel {
    @Id
    @Column (name = "hotelid", unique = true, updatable = false)
    private Integer hotelId;
    @Column (name = "hotelname", unique = false, updatable = true)
    private String hotelName;

    @Column (name = "rating", unique = false, updatable = true)
    private Integer rating;

    @Column (name = "cityname", unique = false, updatable = true)
    private String cityName;

    @Column (name = "owneremail", unique = false, updatable = false)
    private String ownerEmail;

    @Column (name = "description", unique = false, updatable = true)
    private String description;

    @Column (name = "image", unique = false, updatable = true)
    private byte[] image;


    private transient  List<Room> roomList = new ArrayList<Room>();

    public Integer getHotelId() { return hotelId; }
    public String getHotelName() { return hotelName; }
    public Integer getRating() { return rating; }
    public String getCityName() { return cityName; }
    public String getOwnerEmail() { return ownerEmail; }
    public String getDescription() { return description; }

    public int getRoomListLength() { return roomList.size(); }
    public List<Room> getRoomList() { return roomList; }

    public byte[] getImage() { return image; }
    public void setHotelId(Integer hotelId) { this.hotelId = hotelId; }
    public void addItemToRoomList(Room room) {
        roomList.add(room);
    }
    public void setOwnerEmail(String email) {
        this.ownerEmail = email;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "hotelId=" + hotelId +
                ", hotelName='" + hotelName + '\'' +
                ", rating=" + rating +
                ", cityName='" + cityName + '\'' +
                ", ownerEmail='" + ownerEmail + '\'' +
                ", description='" + description + '\'' +
                ", image=" + Arrays.toString(image) +
                ", roomList=" + roomList +
                '}';
    }
}
