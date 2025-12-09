package com.cs613.smp.exn;

public class InvalidFollowRelationExn extends InvalidRequestExn {
    @Override
    public String getMessage() {
        return "Invalid Follow Relation";
    }

    public InvalidFollowRelationExn() {}
}
