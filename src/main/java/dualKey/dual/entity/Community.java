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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserInfo userInfo;

    @Column
    private String title;

    @Column
    private String content;

    protected Community() {
    }

    /**
     * 커뮤니티 생성 매서드
     */
    public static Community createCommunity(UserInfo userInfo, String title, String content) {
        Community community = new Community();
        community.userInfo = userInfo;
        community.title = title;
        community.content = content;
        return community;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
