package com.cs613.smp.repo;

import com.cs613.smp.entity.PostInteraction;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostInteractionRepo extends CrudRepository<PostInteraction, Long> {
    @Query("SELECT * FROM t_post_interaction WHERE pid = :pid AND uid = :uid")
    List<PostInteraction> findByPidAndUid(@Param("pid") Long pid, @Param("uid") Long uid);
}
