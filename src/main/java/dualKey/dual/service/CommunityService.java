package dualKey.dual.service;

import dualKey.dual.entity.Community;
import dualKey.dual.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommunityService {

    void createCommunity(UserInfo userInfo, String title, String content);

    Community selectCommunity(Long communityId);

    Page<Community> findCommunityPage(Pageable pageable);

}
