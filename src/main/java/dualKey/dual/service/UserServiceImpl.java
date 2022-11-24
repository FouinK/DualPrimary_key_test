package dualKey.dual.service;

import dualKey.dual.entity.UserId;
import dualKey.dual.entity.UserInfo;
import dualKey.dual.exception.CustomException;
import dualKey.dual.repository.UserInfoInfoDslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserInfoInfoDslRepository userInfoRepository;

    /**
     * 회원가입 메서드
     */
    @Override
    @Transactional
    public void join(UserInfo userInfo) {

        validateHasNullUserId(userInfo.getUserId());
        vaildateDuplicateUserInfo(userInfo.getUserId());

        userInfoRepository.save(userInfo);

    }

    /**
     *  회원정보 업데이트 메서드
     */
    @Override
    @Transactional
    public void updateUserInfo(UserId userId, String changePhoneNum) {
        UserInfo findUserInfo = userInfoRepository.findByUserId(userId).get();

        findUserInfo.setPhone(changePhoneNum);
    }

    /**
     * 화원 삭제 메서드
     */
    @Override
    @Transactional
    public void deleteUserInfo(UserId userId) {

        validateNotHaveUserInfo(userId);

        userInfoRepository.deleteByUserId(userId);
    }

    /**
     * 회원 삭제 시 회원 존재 하지 않을 경우
     */
    public void validateNotHaveUserInfo(UserId userId) {
        Optional<UserInfo> findUserInfo = userInfoRepository.findByUserId(userId);
        findUserInfo.orElseThrow(
                () -> new CustomException("회원이 존재하지 않으므로 삭제할 수 업습니다.")
        );
    }

    /**
     * 중복회원 검사
     */
    public void vaildateDuplicateUserInfo(UserId userId) {
        Optional<UserInfo> findUserInfo = userInfoRepository.findByUserId(userId);
        findUserInfo.ifPresent(userInfo -> {
            throw new CustomException("이미 회원이 존재함");
        });

//        boolean check = userInfoRepository.existsByUserId(userId);
//        if (check == true) {
//            throw new IllegalStateException("중복회원 존재");
//        }
    }

    /**
     * 메인키 널 값 검사
     */
    public void validateHasNullUserId(UserId userId) {
        if (userId.getUsername() == null || userId.getId() == null) {
            throw new CustomException("메인 키 값은 NULL 일 수 없습니다.");
        }
    }

}
