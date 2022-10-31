package dualKey.dual.service;

import dualKey.dual.entity.Community;
import dualKey.dual.entity.UserInfo;
import dualKey.dual.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;

    @Override
    public void createCommunity(UserInfo userInfo, String title, String content) {
        Community community = Community.createCommunity(userInfo, title, content);
        communityRepository.save(community);
    }

    @Override
    public Community selectCommunity(Long communityId) {
        Optional<Community> findCommunity = communityRepository.findById(communityId);
        return findCommunity.get();
    }
}
