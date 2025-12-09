package com.cs613.smp.exn;

public class InvalidRegistrationRequestExn extends InvalidRequestExn {
    @Override
    public String getMessage() {
        return "Invalid Registration Request";
    }

    public InvalidRegistrationRequestExn() {}
}
