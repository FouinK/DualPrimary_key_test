package dualKey.dual.repository;

import dualKey.dual.entity.UserId;
import dualKey.dual.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoInfoDslRepository extends JpaRepository<UserInfo, Long>, UserInfoQueryDslRepository {

    boolean existsByUserId(UserId userId);

    Optional<UserInfo> findByUserId(UserId userId);

    void deleteByUserId(UserId userId);
}
