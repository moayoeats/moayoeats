package com.moayo.moayoeats.backend.global.security;

import static com.moayo.moayoeats.backend.global.jwt.JwtUtil.AUTHORIZATION_HEADER;

import com.moayo.moayoeats.backend.global.jwt.JwtUtil;
import com.moayo.moayoeats.backend.global.jwt.TokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@AllArgsConstructor
@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final TokenService refreshTokenService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return Stream.of("/login", "/signup")
            .anyMatch(path -> path.matches(request.getRequestURI()));
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest req,
        HttpServletResponse res,
        FilterChain filterChain
    ) throws ServletException, IOException {

        String tokenValue = jwtUtil.getTokenFromRequest(req, AUTHORIZATION_HEADER);
        String url = req.getRequestURI();

        String token = refreshTokenService.relatedIssuanceOfTokens(req, res);
        if (!token.equals("")) {
            tokenValue = token;
        }

        if (StringUtils.hasText(tokenValue)) {
            tokenValue = jwtUtil.substringToken(tokenValue);

            if (!jwtUtil.validateToken(tokenValue)) {
                log.error("Token Error");
                return;
            }

            Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
            try {
                setAuthentication(info.getSubject());
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
            redirect(url, res);
        }
        filterChain.doFilter(req, res);
    }

    // 인증 처리
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
    }

    //url에 따라 redirect
    private void redirect(String url, HttpServletResponse res) throws IOException {
        if (url.equals("/")) {//토큰이 있을때 현재 요청이 /인지 확인하고 인증이 된 상태이면 인증 후 전체글 페이지로 감
            res.sendRedirect("/posts");
        }
        if (url.contains(
            "/moayo/readpost/")) {//토큰이 있을때 로그인 정보 없는 글 조회페이지로 들어오는 요청은 로그인 정보 있는 글 조회페이지로 보냄
            res.sendRedirect("/post/" + getPostId(url));
        }
    }

    private String getPostId(String url) {
        String[] paths = url.split("/");
        return paths[paths.length - 1];
    }
}