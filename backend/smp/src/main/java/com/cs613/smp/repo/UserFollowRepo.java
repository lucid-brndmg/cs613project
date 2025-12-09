package com.cs613.smp.repo;

import com.cs613.smp.entity.dto.UidPair;
import com.cs613.smp.entity.dto.User;
import com.cs613.smp.entity.dto.UserFollow;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface UserFollowRepo extends CrudRepository<UserFollow, UidPair> {
    @Modifying
    @Query("insert into t_user_follow (uid_source, uid_target, time_created) VALUES (:uid_source, :uid_target, :time_created) ON CONFLICT DO NOTHING")
    void insert(@Param("uid_source") Long uidSource, @Param("uid_target") Long uidTarget, @Param("time_created") LocalDateTime timeCreated);

    @Query("select count(*) from t_user_follow where uid_source = :uid")
    Long countFollowingByUid(@Param("uid") Long uid);

    @Query("select count(*) from t_user_follow where uid_target = :uid")
    Long countFollowerByUid(@Param("uid") Long uid);

    @Query("select t_user.* from t_user join t_user_follow on t_user.uid = t_user_follow.uid_target where t_user_follow.uid_source = :uid limit :limit offset :offset")
    List<User> findAllFollowingsByUid(@Param("uid") Long uid, @Param("limit") Integer limit, @Param("offset") Integer offset);

    @Query("select t_user.* from t_user join t_user_follow on t_user.uid = t_user_follow.uid_source where t_user_follow.uid_target = :uid limit :limit offset :offset")
    List<User> findAllFollowersByUid(@Param("uid") Long uid, @Param("limit") Integer limit, @Param("offset") Integer offset);

    @Query("select t_user.* from t_user join t_user_follow on t_user.uid = t_user_follow.uid_target where t_user_follow.uid_source = :uid and t_user.username like concat('%', :username, '%') limit :limit offset :offset")
    List<User> searchFollowingByUid(@Param("username") String username, @Param("uid") Long uid, @Param("limit") Integer limit, @Param("offset") Integer offset);

    @Query("select t_user.* from t_user join t_user_follow on t_user.uid = t_user_follow.uid_source where t_user_follow.uid_target = :uid and t_user.username like concat('%', :username, '%') limit :limit offset :offset")
    List<User> searchFollowerByUid(@Param("username") String username, @Param("uid") Long uid, @Param("limit") Integer limit, @Param("offset") Integer offset);
}
