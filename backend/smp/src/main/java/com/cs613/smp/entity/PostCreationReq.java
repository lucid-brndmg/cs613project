package com.cs613.smp.entity;

import io.swagger.v3.oas.annotations.media.Schema;

public class PostCreationReq {
    @Schema(description = "user id")
    private Long uid;
    @Schema(description = "post content: should not exceed 250 unicode chars by spec")
    private String content;

    @Schema(description = "parent post id for replies")
    private Long parentPid;
    @Schema(description = "reference post id for reposts")
    private Long referencePid;

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
}
