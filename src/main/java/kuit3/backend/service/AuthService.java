package kuit3.backend.service;

import kuit3.backend.common.exception.UserException;
import kuit3.backend.common.exception.jwt.unauthorized.JwtUnauthorizedTokenException;
import kuit3.backend.dao.UserDao;
import kuit3.backend.dto.auth.LoginRequest;
import kuit3.backend.dto.auth.LoginResponse;
import kuit3.backend.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserDao userDao;
    private final JwtProvider jwtProvider;

    public LoginResponse login(LoginRequest authRequest) {
        String nickname = authRequest.getNickname();

        // TODO: 1. 닉네임 유효성 확인
        long userId;
        try {
            userId = userDao.getUserIdByNickname(nickname);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new UserException(NICKNAME_NOT_FOUND);
        }

        // TODO: 2. JWT 갱신
        String updatedJwt = jwtProvider.createToken(nickname, userId);

        return new LoginResponse(userId, updatedJwt);
    }

    public long getUserIdByNickname(String nickname) {
        try {
            return userDao.getUserIdByNickname(nickname);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new JwtUnauthorizedTokenException(TOKEN_MISMATCH);
        }
    }
}
