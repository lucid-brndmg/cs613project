package com.cs613.smp.entity.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("t_user_follow")
public class UserFollow {
    @Id
    private UidPair uidPair;

    private LocalDateTime timeCreated;

    public UserFollow(UidPair uidPair, LocalDateTime timeCreated) {
        this.uidPair = uidPair;
        this.timeCreated = timeCreated;
    }

    public UidPair getUidPair() {
        return uidPair;
    }

    public void setUidPair(UidPair uidPair) {
        this.uidPair = uidPair;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }
}
