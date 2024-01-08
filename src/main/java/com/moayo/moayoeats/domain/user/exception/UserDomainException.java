package com.moayo.moayoeats.domain.user.exception;

import com.moayo.moayoeats.global.exception.GlobalException;

public class UserDomainException extends GlobalException {

    public UserDomainException(final UserErrorCode userErrorCode) {
        super(userErrorCode);
    }
}
