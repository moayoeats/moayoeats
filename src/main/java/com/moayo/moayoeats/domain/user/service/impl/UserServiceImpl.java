package com.moayo.moayoeats.domain.user.service.impl;

import static com.moayo.moayoeats.domain.user.exception.UserErrorCode.ALREADY_EXIST_USER;
import static com.moayo.moayoeats.domain.user.exception.UserErrorCode.NOT_MATCH_PASSWORD;

import com.moayo.moayoeats.domain.user.dto.request.SignupRequest;
import com.moayo.moayoeats.domain.user.entity.User;
import com.moayo.moayoeats.domain.user.exception.UserDomainException;
import com.moayo.moayoeats.domain.user.repository.UserRepository;
import com.moayo.moayoeats.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequest signupReq) {
        String email = signupReq.email();
        String password = passwordEncoder.encode(signupReq.password());
        String checkPassword = signupReq.checkPassword();
        String nickname = signupReq.nickname();

        checkExistUser(email);
        checkMatchPassword(checkPassword, password);

        User user = User.builder()
            .email(email)
            .password(password)
            .nickname(nickname)
            .build();

        userRepository.save(user);
    }

    private void checkExistUser(String email) {

        if(userRepository.existsByEmail(email))
            throw new UserDomainException(ALREADY_EXIST_USER);
    }

    private void checkMatchPassword(
        String rawPassword,
        String encodePassword
    ) {

        if (!passwordEncoder.matches(rawPassword, encodePassword)) {
            throw new UserDomainException(NOT_MATCH_PASSWORD);
        }
    }

}
