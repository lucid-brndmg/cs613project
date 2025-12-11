package com.cs613.smp.entity;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserSearchReq {
    @Schema(nullable = true, description = "optional search keyword of username")
    private String username;

    @Schema(nullable = true, description = "pagination parameter, pass to SQL")
    private Integer limit;
    @Schema(nullable = true, description = "pagination parameter, pass to SQL")
    private Integer offset;

    @Schema(nullable = true, description = "Optional current uid, showing following/follower relation if provided")
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

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
