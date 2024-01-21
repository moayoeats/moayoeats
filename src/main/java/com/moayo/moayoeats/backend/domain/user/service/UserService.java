package com.moayo.moayoeats.backend.domain.user.service;

import com.moayo.moayoeats.backend.domain.user.dto.request.AddressUpdateRequest;
import com.moayo.moayoeats.backend.domain.user.dto.request.InfoUpdateRequest;
import com.moayo.moayoeats.backend.domain.user.dto.request.LoginRequest;
import com.moayo.moayoeats.backend.domain.user.dto.request.PasswordUpdateRequest;
import com.moayo.moayoeats.backend.domain.user.dto.request.SignupRequest;
import com.moayo.moayoeats.backend.domain.user.dto.response.AddressResponse;
import com.moayo.moayoeats.backend.domain.user.dto.response.MyPageResponse;
import com.moayo.moayoeats.backend.domain.user.dto.response.OtherUserPageResponse;
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

    /**
     * @param passwordUpdateReq : 비밀번호 수정에 필요한 요청 dto
     * @param user              : 해당 계정으로 로그인한 사용자
     */
    void updatePassword(PasswordUpdateRequest passwordUpdateReq, User user);

    /**
     * @param user : 해당 계정으로 로그인한 사용자
     * @return : 마이페이지 열람 응답 dto
     */
    MyPageResponse openMyPage(User user);

    /**
     * @param otherUserId : 로그인한 사용자가 아닌 다른 유저
     * @param user        : 해당 계정으로 로그인한 사용자
     * @return : 다른 사람페이지 열람 응답 dto
     */
    OtherUserPageResponse openOtherUserPage(Long otherUserId, User user);

    /**
     * @param addressUpdateReq : 주소 수정시 필요한 요청 dto
     * @param user:            해당 계정으로 로그인한 사용자
     */
    void updateAddress(AddressUpdateRequest addressUpdateReq, User user);

    /**
     *
     * @param user : 주소를 요청하는 사용자
     * @return : 사용자 주소
     */
    public AddressResponse getAddress(User user);
}
