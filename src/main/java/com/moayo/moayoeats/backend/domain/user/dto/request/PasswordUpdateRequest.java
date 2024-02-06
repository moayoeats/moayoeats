package com.moayo.moayoeats.backend.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record PasswordUpdateRequest(
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9!@#$]{8,15}$",
        message = "비밀번호는 a-z, A-Z, 0-9, !@#$ 만 포함하고 8-15자이어야 합니다.")
    String newPassword,
    String checkPassword,
    String password
) {

}
