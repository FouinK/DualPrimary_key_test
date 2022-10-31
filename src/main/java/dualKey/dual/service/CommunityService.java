package dualKey.dual.service;

import dualKey.dual.entity.UserInfo;

public interface CommunityService {

    public void createCommunity(UserInfo userInfo, String title, String content);

}
