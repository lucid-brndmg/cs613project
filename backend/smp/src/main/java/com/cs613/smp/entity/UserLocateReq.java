package com.cs613.smp.entity;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserLocateReq {
    @Schema(nullable = true, description = "Provide ONLY username when upsert, without uid nor uidIdentity")
    private String username;
    @Schema(nullable = true, description = "Provide EITHER username or uid when locating")
    private Long uid;

    @Schema(nullable = true, description = "Optional current uid, locates following/follower relation if provided")
    private Long uidIdentity;

    public Long getUidIdentity() {
        return uidIdentity;
    }

    public void setUidIdentity(Long uidIdentity) {
        this.uidIdentity = uidIdentity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
