package com.cs613.smp.exn;

public class InvalidUIDExn extends InvalidRequestExn {
    @Override
    public String getMessage() {
        return "Invalid UID";
    }

    public InvalidUIDExn() {}
}
