package dualKey.dual.service;

import dualKey.dual.entity.Community;
import dualKey.dual.entity.UserInfo;
import dualKey.dual.entity.UserId;
import dualKey.dual.exception.CustomException;
import dualKey.dual.repository.CommunityRepository;
import dualKey.dual.repository.UserInfoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.transaction.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Autowired
    UserService userService;
    @Autowired
    CommunityService communityService;
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    CommunityRepository communityRepository;
    @Autowired
    EntityManager em;


    @Test
    @DisplayName("듀얼 키 인설트 테스트")
    @Transactional
    public void dualKey_insertTest() {
        // given
        Long id = 3L;
        String username = "테스트 회원명";
        String phone = "01045839103";
        UserId userId = UserId.createUserId(id, username);

        UserInfo userInfo = UserInfo.createUserInfo(userId, phone);

        // when
        userService.join(userInfo);

        // then
        UserInfo findUserInfo = userInfoRepository.findByUserId(userId).get();
        System.out.println(findUserInfo.getUserId().getId());
        System.out.println(findUserInfo.getUserId().getUsername());
        System.out.println(findUserInfo.getPhone());

    }

    @Test
    @DisplayName("듀얼키 하나 존재하지 않는 예외 테스트")
    @Transactional
//    @Rollback(value = false)
    public void dualKey_insertExceptionTest() {
        // given
        long id = 1L;
        String username = "성현";
        UserId userId = UserId.createUserId(id, null);
        String phone = "01045839103";
        UserInfo userInfo = UserInfo.createUserInfo(userId, phone);

        // when
        try {
            userService.join(userInfo);
            em.flush();
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        }

//        then
        assertThrows(CustomException.class, () -> {
            userService.join(userInfo);
        });
    }

    @Test
    @DisplayName("메인 키 값 중복 테스트 (중복회원가입)")
    @Transactional
//    @Rollback(value = false)
    public void duplicate_mainKey() {
        // given
        // 중복아이디 생성
        UserId userId = UserId.createUserId(1L, "userA");

        UserInfo userA = UserInfo.createUserInfo(userId, "01010101");
        UserInfo userB = UserInfo.createUserInfo(userId, "010453");
        userService.join(userA);

        // when
        try {
            userService.join(userB);
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        }

        // then
        assertThrows(CustomException.class, () ->{
            userService.join(userB);
        });
//

    }

    @Test
    @DisplayName("Transactional 휴대폰 번호 업데이트 테스트")
    public void phoneNumUpdate() {
        // given
        UserId userId = UserId.createUserId(1L, "userA");

        UserInfo userA = UserInfo.createUserInfo(userId, "전화 번호 없음");
        userService.join(userA);

        // when
        userService.updateUserInfo(userId, "010 - xxxx - xxxx");

        // then
        UserInfo findUser = userInfoRepository.findByUserId(userId).get();

        assertThat(findUser.getPhone()).isEqualTo("010 - xxxx - xxxx");
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    @Rollback(value = false)
    public void deleteUserInfoTest() {
        // given
        UserId userId = UserId.createUserId(1L, "userA");

        UserInfo userA = UserInfo.createUserInfo(userId, "전화 번호 없음");
        userService.join(userA);

        // when
        userService.deleteUserInfo(userId);

        // then
        assertThrows(NoSuchElementException.class, ()->{
            userInfoRepository.findByUserId(userId).get();
        });
    }

    @Test
    @DisplayName("커뮤니티 생성한 회원정보 삭제")
    @Rollback(value = false)
    public void deleteUserInfoAndCommunityTest() {
        // given
        UserId userId = UserId.createUserId(1L, "userA");
//
        UserInfo userA = UserInfo.createUserInfo(userId, "전화 번호 없음");
        userService.join(userA);
        UserInfo findUserInfo = userInfoRepository.findByUserId(userId).get();
        communityService.createCommunity(findUserInfo, "title", "comment");

        Community findCommunity = communityRepository.findById(1L).get();

        System.out.println("저장된 커뮤니티 타이틀 값 : "+findCommunity.getTitle());

        em.clear();

        // when
        userService.deleteUserInfo(findUserInfo.getUserId());

        // then
        assertThrows(NoSuchElementException.class, () -> {
            userInfoRepository.findByUserId(userId).get();
        });

        assertThrows(NoSuchElementException.class, () -> {
            communityRepository.findById(1L).get();
        });
    }

    @Test
    @DisplayName("1차 캐시 테스트")
    public void 일차캐시테스트() throws HeuristicRollbackException, SystemException, HeuristicMixedException, RollbackException {
        // given
        Long id = 3L;
        String username = "테스트 회원명";
        String phone = "01045839103";
        UserId userId = UserId.createUserId(id, username);

        UserInfo userInfo = UserInfo.createUserInfo(userId, phone);

        em.persist(userInfo);
//        userService.join(userInfo);
//        em.clear();
        // when
        UserInfo firstUserInfo = em.find(UserInfo.class, userId);
        UserInfo secondUserInfo = em.find(UserInfo.class, userId);
        System.out.println("------");       //commit 후 쿼리문 날림
        em.flush();

//        em.flush();
//        UserInfo findUserInfo = userInfoRepository.findByUserId(userId).get();
//        UserInfo secondUserInfo = userInfoRepository.findByUserId(userId).get();
        // then
        Assertions.assertThat(firstUserInfo).isEqualTo(secondUserInfo);
        System.out.println(userInfo.getClass());
        System.out.println(firstUserInfo.getClass());
        System.out.println(secondUserInfo.getClass());

    }

    @Test
    @DisplayName("일차캐시테스트_EM_미사용")
    @Transactional
    public void 일차캐시테스트_EM_미사용() {
        // given
        Long id = 3L;
        String username = "테스트 회원명";
        String phone = "01045839103";
        UserId userId = UserId.createUserId(id, username);

        UserInfo userInfo = UserInfo.createUserInfo(userId, phone);

//        em.persist(userInfo);
        userService.join(userInfo);
        System.out.println("-----");
//        em.flush();
//        em.clear();
        // when
//        UserInfo firstUserInfo = em.find(UserInfo.class, userId);
//        UserInfo secondUserInfo = em.find(UserInfo.class, userId);

        em.flush();

        UserInfo findUserInfo = userInfoRepository.findByUserId(userId).get();
        UserInfo secondUserInfo = userInfoRepository.findByUserId(userId).get();

        // then
        Assertions.assertThat(findUserInfo).isEqualTo(secondUserInfo);
        System.out.println(userInfo.getClass());
        System.out.println(findUserInfo.getClass());
        System.out.println(secondUserInfo.getClass());
    }

}