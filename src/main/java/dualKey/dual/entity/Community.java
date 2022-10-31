package dualKey.dual.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Community {

    @Id
    @GeneratedValue
    @Column(name = "community_id")
    private Long id;

    @ManyToOne
    private UserInfo userInfo;

    @Column
    private String title;

    @Column
    private String content;

    protected Community() {
    }

    public static Community createCommunity(UserInfo userInfo, String title, String content) {
        Community community = new Community();
        community.userInfo = userInfo;
        community.title = title;
        community.content = content;
        return community;
    }
}
