package com.cs613.smp.repo;

import com.cs613.smp.entity.dto.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepo extends CrudRepository<User, Long> {
    @Modifying
    @Query("INSERT INTO t_user (username, time_created, time_login) VALUES (:#{#u.username}, :#{#u.timeCreated}, :#{#u.timeLogin}) ON CONFLICT (username) DO UPDATE SET time_login = :#{#u.timeLogin}")
    void upsert(User u);

    @Query("SELECT * FROM t_user WHERE username = :username")
    User findByUsername(@Param("username") String username);

    @Query("""
select * from t_user
where username like concat('%', :username, '%')
order by
(select count(*) from t_user_follow where t_user.uid = t_user_follow.uid_target) desc,
(select count(*) from t_post where t_user.uid = t_post.uid) desc ,
time_created desc
limit :limit offset :offset""")
    List<User> searchByUsername(@Param("username") String username, @Param("limit") Integer limit, @Param("offset") Integer offset);

    @Query("""
select * from t_user
order by
(select count(*) from t_user_follow where t_user.uid = t_user_follow.uid_target) desc,
(select count(*) from t_post where t_user.uid = t_post.uid) desc ,
time_created desc
limit :limit offset :offset""")
    List<User> findAllRecent(@Param("limit") Integer limit, @Param("offset") Integer offset);
}
