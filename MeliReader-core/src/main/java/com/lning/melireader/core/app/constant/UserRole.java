package com.lning.melireader.core.app.constant;

/**
 * Created by Ning on 2018/11/20.
 */

public enum UserRole {

    VISITOR(0),
    USER(1),
    EDITOR(99);

    private final int type;

    UserRole(int type){
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
