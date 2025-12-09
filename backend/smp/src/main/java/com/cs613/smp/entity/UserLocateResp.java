package com.cs613.smp.entity;

import com.cs613.smp.entity.dto.User;

import java.time.LocalDateTime;

public class UserLocateResp {
    private Long uid;
    private String username;
    private LocalDateTime timeCreated;
    private LocalDateTime timeLogin;
    private Long countFollowing;
    private Long countFollower;
    private Long countPosts;

    private Boolean isFollowing;
    private Boolean isFollower;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getFollowing() {
        return isFollowing;
    }

    public void setFollowing(Boolean following) {
        isFollowing = following;
    }

    public Boolean getFollower() {
        return isFollower;
    }

    public void setFollower(Boolean follower) {
        isFollower = follower;
    }

    public Long getCountPosts() {
        return countPosts;
    }

    public void setCountPosts(Long countPosts) {
        this.countPosts = countPosts;
    }

    public Long getCountFollowing() {
        return countFollowing;
    }

    public void setCountFollowing(Long countFollowing) {
        this.countFollowing = countFollowing;
    }

    public Long getCountFollower() {
        return countFollower;
    }

    public void setCountFollower(Long countFollower) {
        this.countFollower = countFollower;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public LocalDateTime getTimeLogin() {
        return timeLogin;
    }

    public void setTimeLogin(LocalDateTime timeLogin) {
        this.timeLogin = timeLogin;
    }

    public UserLocateResp(Long uid, LocalDateTime timeCreated, LocalDateTime timeLogin, Long countFollowing, Long countFollower, Long countPosts, Boolean isFollowing, Boolean isFollower, String username) {
        this.uid = uid;
        this.timeCreated = timeCreated;
        this.timeLogin = timeLogin;
        this.countFollowing = countFollowing;
        this.countFollower = countFollower;
        this.countPosts = countPosts;
        this.isFollowing = isFollowing;
        this.isFollower = isFollower;
        this.username = username;
    }

    public static UserLocateResp ofUser(User user, Long following, Long follower, Long posts, Boolean isFollowing, Boolean isFollower) {
        return new UserLocateResp(user.getUid(), user.getTimeCreated(), user.getTimeLogin(), following, follower, posts, isFollowing, isFollower, user.getUsername());
    }
}
