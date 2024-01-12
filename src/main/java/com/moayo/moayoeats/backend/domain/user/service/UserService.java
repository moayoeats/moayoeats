package com.moayo.moayoeats.backend.domain.user.service;

import com.moayo.moayoeats.backend.domain.user.dto.request.LoginRequest;
import com.moayo.moayoeats.backend.domain.user.dto.request.SignupRequest;

public interface UserService {

    /**
     * @param signupReq : 회원가입에 필요한 요청 dto
     */
    void signup(SignupRequest signupReq);

    /**
     * @param loginReq : 회원가입에 필요한 요청 dto
     * @return : 생성한 토큰
     */
    String login(LoginRequest loginReq);

}
