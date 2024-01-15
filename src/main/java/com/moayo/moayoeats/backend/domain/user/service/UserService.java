package com.moayo.moayoeats.backend.domain.user.service;

import com.moayo.moayoeats.backend.domain.user.dto.request.InfoUpdateRequest;
import com.moayo.moayoeats.backend.domain.user.dto.request.LoginRequest;
import com.moayo.moayoeats.backend.domain.user.dto.request.SignupRequest;
import com.moayo.moayoeats.backend.domain.user.entity.User;

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

    /**
     * @param infoUpdateReq : 회원수정에 필요한 요청 dto
     * @param user          : 해당 계정으로 로그인한 사용자
     */
    void updateInfo(InfoUpdateRequest infoUpdateReq, User user);
}
