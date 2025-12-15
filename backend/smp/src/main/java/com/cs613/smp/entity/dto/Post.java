package com.cs613.smp.entity.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("t_post")
public class Post {
    @Id
    private Long pid;
    private Long uid;
    private String content;
    private LocalDateTime timeCreated;

    private Long parentPid;
    private Long referencePid;
    private Integer likeCount;
    private Integer repostCount;
    private Integer replyCount;
    private byte[] likersBf;

    @Transient
    private boolean isLiked;

    // No-arg constructor for Spring Data JDBC
    public Post() {
    }

    public Post(Long pid, Long uid, String content, LocalDateTime timeCreated, Long parentPid, Long referencePid, Integer likeCount, Integer repostCount, Integer replyCount, byte[] likersBf) {
        this.pid = pid;
        this.uid = uid;
        this.content = content;
        this.timeCreated = timeCreated;
        this.parentPid = parentPid;
        this.referencePid = referencePid;
        this.likeCount = likeCount != null ? likeCount : 0;
        this.repostCount = repostCount != null ? repostCount : 0;
        this.replyCount = replyCount != null ? replyCount : 0;
        this.likersBf = likersBf;
    }

    public boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }

    public Post(Long pid, Long uid, String content, LocalDateTime timeCreated) {
        this(pid, uid, content, timeCreated, null, null, 0, 0, 0, null);
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

    public Long getParentPid() {
        return parentPid;
    }

    public void setParentPid(Long parentPid) {
        this.parentPid = parentPid;
    }

    public Long getReferencePid() {
        return referencePid;
    }

    public void setReferencePid(Long referencePid) {
        this.referencePid = referencePid;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getRepostCount() {
        return repostCount;
    }

    public void setRepostCount(Integer repostCount) {
        this.repostCount = repostCount;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public byte[] getLikersBf() {
        return likersBf;
    }

    public void setLikersBf(byte[] likersBf) {
        this.likersBf = likersBf;
    }
}
