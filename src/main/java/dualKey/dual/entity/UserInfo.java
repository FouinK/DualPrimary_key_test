package dualKey.dual.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@EqualsAndHashCode
public class UserInfo extends BaseTimeEntity implements Serializable {

    @Id
    @EmbeddedId
    @Column(name = "user_id")
    private UserId userId;


//    @Column(name = "userPhone")
    private String phone;

    public static UserInfo createUserInfo(UserId userId, String phone) {

        UserInfo userInfo = new UserInfo();
        userInfo.userId = userId;
        userInfo.phone = phone;
        return userInfo;
    }

    protected UserInfo() {
    }


//    @Builder
//    public UserInfo(UserId userId, String phone) {
//        this.userId = userId;
//        this.phone = phone;
//    }

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
