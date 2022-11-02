package dualKey.dual.controller;

import dualKey.dual.entity.UserId;
import dualKey.dual.entity.UserInfo;
import dualKey.dual.exception.CustomException;
import dualKey.dual.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입 매핑 메서드
     */
    @PostMapping("/user/join")
    public ResponseEntity<?> createUserInfo(@RequestBody Map<String, String> map) {
        try {
            UserId userId = UserId.createUserId(Long.valueOf(map.get("id")), map.get("username"));
            UserInfo userInfo = UserInfo.createUserInfo(userId, map.get("phone"));
            userService.join(userInfo);
        } catch (NumberFormatException e) {
            throw new CustomException("id 값 입력이 잘못되었습니다.");
        }
        return ResponseEntity.ok("회원가입 완료");
    }

    /**
     *  회원 삭제 매핑
     */
    @DeleteMapping("/user/delete")
    public ResponseEntity<?> deleteUserInfo(@RequestBody Map<String, String> map) {
        UserId userId = UserId.createUserId(Long.valueOf(map.get("id")) ,map.get("username"));
        userService.deleteUserInfo(userId);

        return ResponseEntity.ok("회원 탈퇴 완료");
    }
}
