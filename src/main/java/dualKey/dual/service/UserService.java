package dualKey.dual.service;

import dualKey.dual.entity.UserId;
import dualKey.dual.entity.UserInfo;

import java.util.Map;

public interface UserService {

    void join(UserInfo userInfo);

    void updateUserInfo(UserId userId, String changePhoneNum);
}
