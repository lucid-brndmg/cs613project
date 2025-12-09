package com.cs613.smp.entity.dto;

public record UidPair(Long uidSource, Long uidTarget) {
    public UidPair invert() {
        return new UidPair(this.uidTarget, this.uidSource);
    }
}
