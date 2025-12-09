package com.cs613.smp.entity.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("t_user")
public class User {
    @Id
    private Long uid;
    private String username;
    private LocalDateTime timeCreated;
    private LocalDateTime timeLogin;

    public User(Long uid, String username, LocalDateTime timeCreated, LocalDateTime timeLogin) {
        this.uid = uid;
        this.username = username;
        this.timeCreated = timeCreated;
        this.timeLogin = timeLogin;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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


}
