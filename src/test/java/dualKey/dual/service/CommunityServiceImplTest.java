package dualKey.dual.service;

import dualKey.dual.entity.Community;
import dualKey.dual.entity.UserId;
import dualKey.dual.entity.UserInfo;
import dualKey.dual.repository.UserInfoInfoDslRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;


@SpringBootTest
@Transactional
class CommunityServiceImplTest {

    @Autowired
    UserService userService;
    @Autowired
    UserInfoInfoDslRepository userInfoRepository;
    @Autowired
    CommunityService communityService;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("커뮤니티 생성")
    @Rollback(value = false)
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
    @Rollback(value = false)
    public void selectCommunity() {
        // given
        Long id = 3L;
        String username = "테스트 회원명";
        String phone = "01045839103";
        UserId userId = UserId.createUserId(id, username);
        UserInfo userInfo = UserInfo.createUserInfo(userId, phone);

//        userService.join(userInfo);
        em.persist(userInfo);

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

    @Test
    @DisplayName("커뮤니티 페이지 테스트")
    void findCommunityPageTest() {
        // given
        Long id = 3L;
        String username = "테스트 회원명";
        String phone = "01045839103";
        UserId userId = UserId.createUserId(id, username);
        UserInfo userInfo = UserInfo.createUserInfo(userId, phone);

//        userService.join(userInfo);
        em.persist(userInfo);

//        Optional<UserInfo> findUserInfo = userInfoRepository.findByUserId(userId);
//        communityService.createCommunity(findUserInfo.get(), "title__", "content__");

        Community community = Community.createCommunity(userInfo, "title__", "content__");
        em.persist(community);

        //when
        Page<Community> communityPage = communityService.findCommunityPage(Pageable.unpaged());

        //then
        System.out.println("communityPage = " + communityPage.getTotalPages());

    }
}