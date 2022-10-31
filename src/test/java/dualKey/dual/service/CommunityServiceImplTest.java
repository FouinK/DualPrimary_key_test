package dualKey.dual.service;

import dualKey.dual.entity.UserId;
import dualKey.dual.entity.UserInfo;
import dualKey.dual.repository.UserInfoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
}