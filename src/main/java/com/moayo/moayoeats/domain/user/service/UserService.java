package com.moayo.moayoeats.domain.user.service;

import com.moayo.moayoeats.domain.user.dto.request.SignupRequest;

public interface UserService {

    /**
     *
     * @param signupReq : 회원가입에 필요한 요청 dto
     */
    void signup(SignupRequest signupReq);

}
