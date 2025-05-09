package org.example.militarysystem.exceptions;

import lombok.Getter;
import org.example.militarysystem.utils.userUtils.UserStatus;

@Getter
public class UserInWrongStatusException extends Exception {
    private final UserStatus userStatus;
    public UserInWrongStatusException(UserStatus userStatus) {
        super(userStatus.toString());
        this.userStatus = userStatus;
    }
}
