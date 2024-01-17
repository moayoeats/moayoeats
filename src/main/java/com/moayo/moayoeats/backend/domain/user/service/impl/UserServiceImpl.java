package com.moayo.moayoeats.backend.domain.user.service.impl;

import com.moayo.moayoeats.backend.domain.review.service.impl.ReviewServiceImpl;
import com.moayo.moayoeats.backend.domain.user.dto.request.InfoUpdateRequest;
import com.moayo.moayoeats.backend.domain.user.dto.request.LoginRequest;
import com.moayo.moayoeats.backend.domain.user.dto.request.PasswordUpdateRequest;
import com.moayo.moayoeats.backend.domain.user.dto.request.SignupRequest;
import com.moayo.moayoeats.backend.domain.user.dto.response.MyPageResponse;
import com.moayo.moayoeats.backend.domain.user.dto.response.OtherUserPageResponse;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import com.moayo.moayoeats.backend.domain.user.exception.UserErrorCode;
import com.moayo.moayoeats.backend.domain.user.repository.UserRepository;
import com.moayo.moayoeats.backend.domain.user.service.UserService;
import com.moayo.moayoeats.backend.global.exception.GlobalException;
import com.moayo.moayoeats.backend.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ReviewServiceImpl reviewServiceImpl;

    public void signup(SignupRequest signupReq) {
        String email = signupReq.email();
        String password = passwordEncoder.encode(signupReq.password());
        String checkPassword = signupReq.checkPassword();
        String nickname = signupReq.nickname();

        checkAlreadyExistUser(email);
        checkMatchPassword(checkPassword, password);

        User user = User.builder()
            .email(email)
            .password(password)
            .nickname(nickname)
            .build();

        userRepository.save(user);
    }

    public String login(LoginRequest loginReq) {
        String email = loginReq.email();
        String password = loginReq.password();

        User user = checkNotExistUser(email);
        checkMatchPassword(password, user.getPassword());
        return jwtUtil.createToken(email);
    }

    @Transactional
    public void updateInfo(InfoUpdateRequest infoUpdateReq, User user) {
        String nickname = infoUpdateReq.nickname();
        String password = infoUpdateReq.password();

        checkMatchPassword(password, user.getPassword());
        user.updateInfo(nickname);
        userRepository.save(user);
    }

    @Transactional
    public void updatePassword(PasswordUpdateRequest passwordUpdateReq, User user) {

        String newPassword = passwordEncoder.encode(passwordUpdateReq.newPassword());
        String checkPassword = passwordUpdateReq.checkPassword();
        String password = passwordUpdateReq.password();

        checkMatchPassword(password, user.getPassword()); // 원래 비밀번호에 대한 확인
        checkMatchPassword(checkPassword, newPassword); // 새로 설정할 비밀번호에 대한 확인

        user.updatePassword(newPassword);
        userRepository.save(user);
    }

    public MyPageResponse openMyPage(User user) {

        User existUser = checkNotExistUser(user.getEmail());
        return MyPageResponse.builder()
            .nickname(existUser.getNickname())
            .email(existUser.getEmail())
            .score(reviewServiceImpl.getAvgScore(existUser))
            .reviews(reviewServiceImpl.getReviews(existUser))
            .pastOrderList(reviewServiceImpl.getOrders(existUser))
            .build();
    }

    public OtherUserPageResponse openOtherUserPage(Long otherUserId, User user) {

        if (!userRepository.existsByEmail(user.getEmail())) {
            throw new GlobalException(UserErrorCode.NOT_EXIST_USER);
        }

        User otherUser = userRepository.findById(otherUserId)
            .orElseThrow(() -> new GlobalException(UserErrorCode.NOT_EXIST_USER));

        return OtherUserPageResponse.builder()
            .nickname(otherUser.getNickname())
            .score(reviewServiceImpl.getAvgScore(otherUser))
            .reviews(reviewServiceImpl.getReviews(otherUser))
            .build();
    }

    private void checkAlreadyExistUser(String email) {

        if (userRepository.existsByEmail(email)) {
            throw new GlobalException(UserErrorCode.ALREADY_EXIST_USER);
        }
    }

    private User checkNotExistUser(String email) {

        return userRepository.findByEmail(email)
            .orElseThrow(() -> new GlobalException(UserErrorCode.NOT_EXIST_USER));
    }

    private void checkMatchPassword(
        String rawPassword,
        String encodePassword
    ) {

        if (!passwordEncoder.matches(rawPassword, encodePassword)) {
            throw new GlobalException(UserErrorCode.NOT_MATCH_PASSWORD);
        }
    }

}
