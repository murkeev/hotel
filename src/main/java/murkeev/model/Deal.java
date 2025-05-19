package murkeev.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Entity
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "deal")
@Data
public class Deal {
    @Id
    @Column(name="dealid", unique = true, updatable = false)
    private Integer dealId;
    @Column(name="datebegin", unique = false, updatable = false)
    private Date dateBegin;
    @Column(name="dateend", unique = false, updatable = false)
    private Date dateEnd;
    @Column(name="total", unique = false, updatable = false)
    private Integer total;
    @Column(name="roomid", unique = false, updatable = false)
    private Integer roomId;
    @Column(name="email", unique = false, updatable = false)
    private String email;
    private transient Room room;
    private transient String phone;

    public Integer getDealId() {return dealId;}
    public Date getDateBegin() {return dateBegin;}
    public Date getDateEnd() {return dateEnd;}
    public Integer getTotal() {return total;}
    public Integer getRoomId() {return roomId;}
    public String getEmail() {return email;}

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
    public void setDealId(Integer dealId) {
        this.dealId = dealId;
    }
    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }
    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
