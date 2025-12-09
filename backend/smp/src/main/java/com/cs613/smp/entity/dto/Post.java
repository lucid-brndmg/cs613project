package com.cs613.smp.entity.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("t_post")
public class Post {
    @Id
    private Long pid;
    private Long uid;
    private String content;
    private LocalDateTime timeCreated;

    public Post(Long pid, Long uid, String content, LocalDateTime timeCreated) {
        this.pid = pid;
        this.uid = uid;
        this.content = content;
        this.timeCreated = timeCreated;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }
}
