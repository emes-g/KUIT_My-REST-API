package kuit.server.service;

import kuit.server.common.exception.DatabaseException;
import kuit.server.common.exception.UserException;
import kuit.server.dao.UserDao;
import kuit.server.dto.user.PostUserRequest;
import kuit.server.dto.user.PostUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

        validateNickname(nickname);
        int affectedRows = userDao.updateNickname(userId, nickname);
        if (affectedRows != 1) {
            throw new DatabaseException(DATABASE_ERROR);
        }
    }

    public void updatePhoneNumber(long userId, String phoneNumber) {
        log.info("[UserService.updatePhoneNumber]");

        int affectedRows = userDao.updatePhoneNumber(userId, phoneNumber);
        if (affectedRows != 1) {
            throw new DatabaseException(DATABASE_ERROR);
        }
    }

    public void updateStatus(long userId, String status) {
        log.info("[UserService.updateStatus]");

        int affectedRows = userDao.updateStatus(userId, status);
        if (affectedRows != 1) {
            throw new DatabaseException(DATABASE_ERROR);
        }
    }

    public void updateUserAllInfo(long userId, String nickname, String phoneNumber, String status) {
        log.info("[UserService.updateUserAllInfo]");

        int affectedRows = userDao.updateUserAllInfo(userId, nickname, phoneNumber, status);
        if (affectedRows != 1) {
            throw new DatabaseException(DATABASE_ERROR);
        }
    }
}
