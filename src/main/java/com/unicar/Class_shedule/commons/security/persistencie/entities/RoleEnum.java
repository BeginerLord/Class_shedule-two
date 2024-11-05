package com.unicar.Class_shedule.commons.security.persistencie.entities;


import com.unicar.Class_shedule.commons.utils.constants.RoleConstants;

public enum RoleEnum {

    ADMIN(RoleConstants.ROLE_ADMIN),
    STUDENT(RoleConstants.ROLE_STUDENT),
    TEACHER(RoleConstants.ROLE_TEACHER);

    private final String roleName;

    RoleEnum(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
