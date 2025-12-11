package com.cs613.smp.entity;

import io.swagger.v3.oas.annotations.media.Schema;

public class PostCreationReq {
    @Schema(description = "user id")
    private Long uid;
    @Schema(description = "post content: should not exceed 250 unicode chars by spec")
    private String content;

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
}
