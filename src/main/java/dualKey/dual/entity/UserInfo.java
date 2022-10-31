package dualKey.dual.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@EqualsAndHashCode
//@IdClass(Orders.class)
public class UserInfo extends BaseTimeEntity implements Serializable {
    protected UserInfo() {
    }

    @Id
    @EmbeddedId
    @Column(name = "user_id")
    private UserId userId;


    @Column(name = "userPhone")
    private String phone;


    public static UserInfo createUserInfo(UserId userId, String phone) {
        UserInfo userInfo = new UserInfo();
        userInfo.userId = userId;
        userInfo.phone = phone;
        return userInfo;
    }

    @Builder
    public UserInfo(UserId userId, String phone) {
        this.userId = userId;
        this.phone = phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    /*

        @Id
        @Column(name = "orders_id")
        private Long id;

        @Id
        @Column(name = "username")
        private String username;
    */
}
