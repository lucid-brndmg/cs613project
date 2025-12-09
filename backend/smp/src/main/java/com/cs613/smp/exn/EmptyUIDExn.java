package com.cs613.smp.exn;

public class EmptyUIDExn extends InvalidRequestExn {
    @Override
    public String getMessage() {
        return "No UID Provided";
    }

    public EmptyUIDExn() {}
}
