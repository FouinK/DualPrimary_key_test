package dualKey.dual.service;

import dualKey.dual.entity.Community;
import dualKey.dual.entity.UserId;
import dualKey.dual.entity.UserInfo;
import dualKey.dual.repository.UserInfoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommunityServiceImplTest {

    @Autowired
    UserService userService;
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    CommunityService communityService;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("")
    public void createCommunityTest() {
        // given
        Long id = 3L;
        String username = "테스트 회원명";
        String phone = "01045839103";
        UserId userId = UserId.createUserId(id, username);
        UserInfo userInfo = UserInfo.createUserInfo(userId, phone);
        userService.join(userInfo);
        Optional<UserInfo> findUserInfo = userInfoRepository.findByUserId(userId);

        // when
        communityService.createCommunity(findUserInfo.get(), "title__", "content__");

        // then
    }

    @Test
    @DisplayName("영속성 테스트")
    public void selectCommunity() {
        // given
        Long id = 3L;
        String username = "테스트 회원명";
        String phone = "01045839103";
        UserId userId = UserId.createUserId(id, username);
        UserInfo userInfo = UserInfo.createUserInfo(userId, phone);

//        userService.join(userInfo);
//        em.persist(userInfo);

//        Optional<UserInfo> findUserInfo = userInfoRepository.findByUserId(userId);
//        communityService.createCommunity(findUserInfo.get(), "title__", "content__");

        Community community = Community.createCommunity(userInfo, "title__", "content__");
        em.persist(community);
//        em.persist(userInfo);
        // when
        Community selectCommunity = communityService.selectCommunity(1L);

        // then
        UserInfo communityUserInfo = selectCommunity.getUserInfo();
        System.out.println(communityUserInfo.getPhone());


    }
}