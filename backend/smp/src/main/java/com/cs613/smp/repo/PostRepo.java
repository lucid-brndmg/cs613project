package com.cs613.smp.repo;

import com.cs613.smp.entity.dto.Post;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepo extends CrudRepository<Post, Long> {
    @Query("select count(*) from t_post where uid = :uid")
    Long countPostsByUid(@Param("uid") Long uid);

    @Query("select t_post.* from t_post join t_user_follow on t_user_follow.uid_target = t_post.uid where t_user_follow.uid_source = :uid and t_post.time_created >= :from order by t_post.time_created desc limit :limit offset :offset")
    List<Post> findAllFeedFrom(@Param("uid") Long uid, @Param("from") LocalDateTime from, @Param("limit") Integer limit, @Param("offset") Integer offset);

    @Query("select t_post.* from t_post join t_user_follow on t_user_follow.uid_target = t_post.uid where t_post.content like concat('%', :content, '%') and t_user_follow.uid_source = :uid and t_post.time_created >= :from order by t_post.time_created desc limit :limit offset :offset")
    List<Post> searchAllFeed(@Param("content") String content, @Param("uid") Long uid, @Param("from") LocalDateTime from, @Param("limit") Integer limit, @Param("offset") Integer offset);


    @Query("select * from t_post where content like concat('%', :content, '%') order by time_created desc limit :limit offset :offset")
    List<Post> search(@Param("content") String content, @Param("limit") Integer limit, @Param("offset") Integer offset);

    @Query("select * from t_post where uid = :uid and t_post.time_created >= :from order by time_created desc limit :limit offset :offset")
    List<Post> findUid(@Param("uid") Long uid, @Param("from") LocalDateTime from, @Param("limit") Integer limit, @Param("offset") Integer offset);

    @Query("select * from t_post where content like concat('%', :content, '%') and uid = :uid order by time_created desc limit :limit offset :offset")
    List<Post> searchUid(@Param("content") String content, @Param("uid") Long uid, @Param("limit") Integer limit, @Param("offset") Integer offset);

    @Query("select * from t_post order by time_created desc limit :limit offset :offset")
    List<Post> findAllRecent(@Param("limit") Integer limit, @Param("offset") Integer offset);
}
