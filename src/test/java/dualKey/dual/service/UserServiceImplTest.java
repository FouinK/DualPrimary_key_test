package dualKey.dual.service;

import dualKey.dual.entity.UserInfo;
import dualKey.dual.entity.UserId;
import dualKey.dual.repository.UserInfoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Autowired
    UserService userService;
    @Autowired
    UserInfoRepository userInfoRepository;
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
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

//        then
        assertThrows(IllegalStateException.class, () -> {
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
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        // then
        assertThrows(IllegalStateException.class, () ->{
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

}