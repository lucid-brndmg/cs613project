package com.cs613.smp.entity;

import io.swagger.v3.oas.annotations.media.Schema;

public class PostLocateReq {
    @Schema(description = "mandatory post id")
    private Long pid;

    @Schema(description = "optional viewer uid for checking like status")
    private Long viewerUid;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getViewerUid() {
        return viewerUid;
    }

    public void setViewerUid(Long viewerUid) {
        this.viewerUid = viewerUid;
    }
}
