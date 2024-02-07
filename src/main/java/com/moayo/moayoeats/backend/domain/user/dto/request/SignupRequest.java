package com.moayo.moayoeats.backend.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record SignupRequest(
    @Email(message = "email 올바른 형식의 이메일 주소여야 합니다.")
    String email,
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9!@#$]{8,15}$",
        message = "비밀번호는 a-z, A-Z, 0-9, !@#$ 만 포함하고 8-15자이어야 합니다.")
    String password,
    String checkPassword,
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,20}$",
        message = "닉네임은 a-z, A-Z, 0-9, 한글 만 포함하고 2-20자이어야 합니다.")
    String nickname) {

}
