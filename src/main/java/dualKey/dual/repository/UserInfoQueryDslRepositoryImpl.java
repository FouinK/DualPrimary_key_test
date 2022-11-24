package dualKey.dual.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dualKey.dual.entity.UserId;

import static dualKey.dual.entity.QUserInfo.*;

public class UserInfoQueryDslRepositoryImpl implements UserInfoQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public UserInfoQueryDslRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public int changePhone(String phone) {
        long cnt = queryFactory
                .update(userInfo)
                .set(userInfo.phone, phone)
                .where(userInfo.userId.eq(UserId.createUserId(3L, "테스트 회원명")))
                .execute();
        return (int) cnt;
    }
}
