package com.hanghaemini4.jaranghalram.jwt;

import com.hanghaemini4.jaranghalram.dto.TokenDto;
import com.hanghaemini4.jaranghalram.entity.RefreshToken;
import com.hanghaemini4.jaranghalram.repository.RefreshTokenRepository;
import com.hanghaemini4.jaranghalram.security.UserDetailServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final UserDetailServiceImpl userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_TOKEN_HEADER = "RefreshToken";
    private static final String BEARER_PREFIX = "Bearer ";
    public static final long ACCESS_TOKEN_TIME = 60 * 60 * 1000L * 24 * 365;
    public static final long REFRESH_TOKEN_TIME = 60 * 60 * 1000L * 24 * 365;


    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // header 토큰을 가져오기
    public String resolveToken(HttpServletRequest request, String tokenType) {
        String bearerToken = tokenType.equals("Authorization") ? request.getHeader(AUTHORIZATION_HEADER) :request.getHeader(REFRESH_TOKEN_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
    //전체 토큰 생성
    public TokenDto createAllToken(String userId) {
        return new TokenDto(createToken(userId, ACCESS_TOKEN_TIME), createToken(userId, REFRESH_TOKEN_TIME));
    }

    // 토큰 생성
    public String createToken(String userNickName, long tokenType) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(userNickName)
                        .setExpiration(new Date(date.getTime() + tokenType))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // 토큰에서 userNickName 가져오는 기능
    public String getUserNickName(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }


    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
    // 인증 객체 생성
    public Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    //추가
    public Boolean refreshTokenValidation(String token) {

        // 1차 토큰 검증
        if(!validateToken(token)){
            return false;
        }
        // DB에 저장한 토큰 비교
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUserNickName(getUserNickName(token));

        return refreshToken.isPresent() && token.equals(refreshToken.get().getRefreshToken().substring(7));
    }

}