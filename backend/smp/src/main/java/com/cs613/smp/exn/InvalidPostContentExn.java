package com.cs613.smp.exn;

public class InvalidPostContentExn extends InvalidRequestExn {
    @Override
    public String getMessage() {
        return "Invalid Post Content";
    }

    public InvalidPostContentExn() {}
}
