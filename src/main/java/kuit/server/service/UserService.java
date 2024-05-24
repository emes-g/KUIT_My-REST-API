package kuit.server.service;

import kuit.server.common.exception.DatabaseException;
import kuit.server.common.exception.UserException;
import kuit.server.dao.UserDao;
import kuit.server.dto.user.GetUserResponse;
import kuit.server.dto.user.PostUserRequest;
import kuit.server.dto.user.PostUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static kuit.server.common.status.BaseExceptionResponseStatus.DATABASE_ERROR;
import static kuit.server.common.status.BaseExceptionResponseStatus.DUPLICATE_NICKNAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public PostUserResponse signUp(PostUserRequest postUserRequest) {
        log.info("[UserService.signUp]");

        // TODO: 1. validation (중복 검사)
        validateNickname(postUserRequest.getNickname());

        // TODO: 2. DB insert & userId 반환
        long userId = userDao.createUser(postUserRequest);

        return new PostUserResponse(userId);
    }

    public void validateNickname(String nickname) {
        if (userDao.hasDuplicateNickName(nickname)) {
            throw new UserException(DUPLICATE_NICKNAME);
        }
    }

    public void updateNickname(long userId, String nickname) {
        log.info("[UserService.updateNickname]");

        // TODO: 1. validation (중복 검사)
        validateNickname(nickname);
        
        // TODO: 2. 회원 닉네임 수정
        int affectedRows = userDao.updateNickname(userId, nickname);
        if (affectedRows != 1) {
            throw new DatabaseException(DATABASE_ERROR);
        }
    }

    public void updatePhoneNumber(long userId, String phoneNumber) {
        log.info("[UserService.updatePhoneNumber]");

        // TODO: 회원 핸드폰 번호 수정
        int affectedRows = userDao.updatePhoneNumber(userId, phoneNumber);
        if (affectedRows != 1) {
            throw new DatabaseException(DATABASE_ERROR);
        }
    }

    public void updateStatus(long userId, String status) {
        log.info("[UserService.updateStatus]");

        // TODO: 회원 상태 수정
        int affectedRows = userDao.updateStatus(userId, status);
        if (affectedRows != 1) {
            throw new DatabaseException(DATABASE_ERROR);
        }
    }

    public void updateUserAllInfo(long userId, String nickname, String phoneNumber, String status) {
        log.info("[UserService.updateUserAllInfo]");

        // TODO: 회원 전체 정보 수정
        int affectedRows = userDao.updateUserAllInfo(userId, nickname, phoneNumber, status);
        if (affectedRows != 1) {
            throw new DatabaseException(DATABASE_ERROR);
        }
    }

    public List<GetUserResponse> getUsers() {
        log.info("[UserService.getUsers]");

        // TODO: 전체 회원 조회
        return userDao.getUsers();
    }
}
