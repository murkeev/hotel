package murkeev.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "image")
@Data
public class Image {
    @Id
    @Column(name = "idimage")
    private Integer imageId;
    @Column (name = "image", unique = false, updatable = true)
    private byte[] image;
    @Column (name = "roomid", unique = false, updatable = false)
    private Integer roomId;

    public byte[] getImage() {
        return image;
    }
    public Integer getImageId() {
        return imageId;
    }
    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }
    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }
}
