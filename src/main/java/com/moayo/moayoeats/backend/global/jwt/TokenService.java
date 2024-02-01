package com.moayo.moayoeats.backend.global.jwt;

import static com.moayo.moayoeats.backend.global.jwt.JwtUtil.REFRESH_TOKEN_TIME;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final RedisTemplate<String, String> redisTemplate;
    private final JwtUtil jwtUtil;

    public String relatedIssuanceOfTokens(
        HttpServletRequest req,
        HttpServletResponse res
    ) throws IOException {

        String accessToken = jwtUtil.getTokenFromRequest(req);
        String refreshToken = jwtUtil.getRefreshTokenFromRequest(req);

        if (StringUtils.hasText(accessToken) && StringUtils.hasText(refreshToken)) {
            accessToken = jwtUtil.substringToken(accessToken);
            refreshToken = jwtUtil.substringToken(refreshToken);

            if (jwtUtil.validateToken(accessToken, res)) { // accessToken 유효
                Claims info = jwtUtil.getUserInfoFromToken(refreshToken);

                try {
                    if (info == null) {
                        info = jwtUtil.getUserInfoFromToken(accessToken);
                    }
                    String email = info.getSubject();
                    if (getRefreshToken(email) == null) { // refreshToken 만료시
                        String newRefreshToken = jwtUtil.createRefreshToken(
                            email); // refreshToken 생성
                        saveRefreshToken(email, newRefreshToken);
                        jwtUtil.addRefreshJwtToCookie(newRefreshToken, res);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("refresh token 생성 실패", e);
                }
            } else { // accessToken 만료
                if (jwtUtil.validateToken(refreshToken, res)) { // refreshToken 유효
                    Claims info = jwtUtil.getUserInfoFromToken(refreshToken);
                    String email = info.getSubject();
                    String newAccessToken = jwtUtil.createToken(email);

                    if (!newAccessToken.equals("")) {
                        jwtUtil.addJwtToCookie(newAccessToken, res);
                        return newAccessToken;
                    }
                }
                res.sendRedirect("/login");
            }
        }
        return "";
    }

    // refresh token 저장
    public void saveRefreshToken(String email, String refreshToken) {

        redisTemplate.opsForValue()
            .set(email, refreshToken, REFRESH_TOKEN_TIME, TimeUnit.MILLISECONDS);
    }

    // refresh token 조회
    public String getRefreshToken(String email) {

        return redisTemplate.opsForValue().get(email);
    }

    // refresh token 삭제
    public void deleteRefreshToken(String email) {

        redisTemplate.opsForValue().getAndDelete(email);
    }
}
