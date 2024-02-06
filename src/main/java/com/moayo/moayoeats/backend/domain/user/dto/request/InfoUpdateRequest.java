package com.moayo.moayoeats.backend.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record InfoUpdateRequest(
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,20}$",
        message = "닉네임은 a-z, A-Z, 0-9, 한글 만 포함하고 2-20자이어야 합니다.")
    String nickname,
    String password
) {

}
