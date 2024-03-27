package com.hblog.member.application;

import com.hblog.member.domain.RefreshToken;
import com.hblog.member.domain.UserEntity;
import com.hblog.member.infra.RefreshTokenRepository;
import com.hblog.member.infra.UserRepository;
import com.hblog.member.jwt.JwtTokenProvider;
import com.hblog.member.jwt.Token;
import com.hblog.member.util.AESUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AESUtil aesUtil;
    private final UserRepository userRepository;
    public void saveRefreshToken(Long userIdx, String refreshToken) {
        String encryptedToken = null;
        try {
            encryptedToken = aesUtil.encrypt(refreshToken);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        RefreshToken token = RefreshToken.builder()
                .userIdx(String.valueOf(userIdx))
                .token(encryptedToken)
                .build();
        refreshTokenRepository.save(token);
    }


    public String findRefreshToken(String refreshToken) {
        // 토큰하고 redis 값 복호화해서 비교
        String jwt = refreshToken.replace("Bearer ", "");
        Claims claims = jwtTokenProvider.parseJwt(jwt);
        String userIdx = (String) claims.get("sub");
        RefreshToken token = refreshTokenRepository.findByIdx(userIdx).orElseThrow();
        String decrypted;
        try {
            decrypted = aesUtil.decrypt(token.getToken());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(!jwt.equals(decrypted)){
            throw new IllegalArgumentException();
        }
        // 같으면 엑세스토큰 발급
        UserEntity userEntity = userRepository.findByUserIdx(userIdx).orElseThrow();
        // 아직 role 계획은 없음
        Token accessToken = jwtTokenProvider.createAccessToken(userEntity.getIdx(), userEntity.getUserId(), "");
        return accessToken.getAccessToken();
    }
}
