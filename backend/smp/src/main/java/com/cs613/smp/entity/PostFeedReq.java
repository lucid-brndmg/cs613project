package com.cs613.smp.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class PostFeedReq {
    @Schema(nullable = true, description = "user id, mandatory for /feed, optional for /search")
    private Long uid;
    @Schema(nullable = true, description = "optional date-time (in ISO-8601 format) filter that returns all post AFTER this date")
    private LocalDateTime timeFrom;

    @Schema(nullable = true, description = "keyword")
    private String search;

    @Schema(nullable = true, description = "pagination parameter, pass to SQL")
    private Integer limit;
    @Schema(nullable = true, description = "pagination parameter, pass to SQL")
    private Integer offset;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
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

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public LocalDateTime getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(LocalDateTime timeFrom) {
        this.timeFrom = timeFrom;
    }
}
