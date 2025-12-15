package com.cs613.smp.entity;

import io.swagger.v3.oas.annotations.media.Schema;

public class PostInteractionReq {
    @Schema(description = "post id")
    private Long pid;
    @Schema(description = "user id")
    private Long uid;

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
}
