package dualKey.dual.service;

import dualKey.dual.entity.UserId;
import dualKey.dual.entity.UserInfo;
import dualKey.dual.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserInfoRepository userInfoRepository;

    @Override
    public void join(UserInfo userInfo) {

        validateHasNullUserId(userInfo.getUserId());
        vaildateDuplicateUserInfo(userInfo.getUserId());

        userInfoRepository.save(userInfo);

    }

    /**
     * 중복회원 검사
     * @param userId
     */
    public void vaildateDuplicateUserInfo(UserId userId) {
        Optional<UserInfo> findUserInfo = userInfoRepository.findByUserId(userId);
        findUserInfo.ifPresent(userInfo -> {
            throw new IllegalStateException("이미 회원이 존재함");
        });

//        boolean check = userInfoRepository.existsByUserId(userId);
//        if (check == true) {
//            throw new IllegalStateException("중복회원 존재");
//        }
    }

    /**
     * 메인키 널 값 검사
     * @param userId
     */
    public void validateHasNullUserId(UserId userId) {
        if (userId.getUsername() == null || userId.getId() == null) {
            throw new IllegalStateException("메인 키 값은 NULL 일 수 없습니다.");
        }
    }

}
