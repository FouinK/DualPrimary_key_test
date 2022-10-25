package dualKey.dual.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@EqualsAndHashCode
//@IdClass(Orders.class)
public class UserInfo implements Serializable {
    protected UserInfo() {
    }

    @Id
    @EmbeddedId
    private UserId userId;


    @Column(name = "userPhone")
    private String phone;


    public static UserInfo createUserInfo(UserId userId, String phone) {
        UserInfo userInfo = new UserInfo();
        userInfo.userId = userId;
        userInfo.phone = phone;
        return userInfo;
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
