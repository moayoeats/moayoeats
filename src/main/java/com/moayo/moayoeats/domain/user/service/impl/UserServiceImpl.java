package com.moayo.moayoeats.domain.user.service.impl;

import com.moayo.moayoeats.domain.user.dto.request.LoginRequest;
import com.moayo.moayoeats.domain.user.dto.request.SignupRequest;
import com.moayo.moayoeats.domain.user.entity.User;
import com.moayo.moayoeats.domain.user.exception.UserErrorCode;
import com.moayo.moayoeats.domain.user.repository.UserRepository;
import com.moayo.moayoeats.domain.user.service.UserService;
import com.moayo.moayoeats.global.exception.GlobalException;
import com.moayo.moayoeats.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

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
