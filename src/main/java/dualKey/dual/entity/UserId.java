package dualKey.dual.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class UserId implements Serializable {
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String username;

    public static UserId createUserId(Long id, String username) {
        UserId userId = new UserId();
        userId.id = id;
        userId.username = username;
        return userId;
    }

    protected UserId() {

    }
}
