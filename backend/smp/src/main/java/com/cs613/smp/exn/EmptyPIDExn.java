package com.cs613.smp.exn;

public class EmptyPIDExn extends InvalidRequestExn {
    @Override
    public String getMessage() {
        return "No PID Provided";
    }

    public EmptyPIDExn() {}
}
