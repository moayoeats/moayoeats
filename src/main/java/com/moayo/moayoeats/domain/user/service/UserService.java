package com.moayo.moayoeats.domain.user.service;

import com.moayo.moayoeats.domain.user.dto.request.SignupRequest;

public interface UserService {

    void signup(SignupRequest signupRequest);
}
