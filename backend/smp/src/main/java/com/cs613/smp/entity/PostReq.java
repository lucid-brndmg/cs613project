package com.cs613.smp.entity;

import io.swagger.v3.oas.annotations.media.Schema;

public class PostReq {
    @Schema(description = "ignored for create, mandatory for remove and locate")
    private Long pid;
    @Schema(description = "mandatory for create, ignored for remove")
    private Long uid;
    @Schema(description = "should not exceed 250 unicode chars by spec")
    private String content;

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
}
