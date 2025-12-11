package com.cs613.smp.entity;

import io.swagger.v3.oas.annotations.media.Schema;

public class PostLocateReq {
    @Schema(description = "mandatory post id")
    private Long pid;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }
}
