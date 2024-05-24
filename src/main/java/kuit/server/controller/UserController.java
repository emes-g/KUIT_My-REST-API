package kuit.server.controller;

import kuit.server.common.BaseResponse;
import kuit.server.common.exception.UserException;
import kuit.server.dto.user.*;
import kuit.server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kuit.server.common.status.BaseExceptionResponseStatus.INVALID_USER_STATUS;
import static kuit.server.common.status.BaseExceptionResponseStatus.INVALID_USER_VALUE;
import static kuit.server.util.BindingResultUtils.getErrorMessages;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * 회원 가입
     */
    @PostMapping("")
    public BaseResponse<PostUserResponse> signUp(@Validated @RequestBody PostUserRequest postUserRequest, BindingResult bindingResult) {
        log.info("[UserController.signUp]");

        if (bindingResult.hasErrors()) {
            throw new UserException(INVALID_USER_VALUE, getErrorMessages(bindingResult));
        }
        return new BaseResponse<>(userService.signUp(postUserRequest));
    }

    /**
     * 회워 닉네임 변경
     *
     * @param userId               회원 ID
     * @param patchNicknameRequest 닉네임 변경 요청
     * @param bindingResult        Validation 오류 보관 객체
     * @return 예외 혹은 응답
     */
    @PatchMapping("/{userId}/nickname")
    public BaseResponse<String> updateNickname(@PathVariable long userId,
                                               @Validated @RequestBody PatchNicknameRequest patchNicknameRequest, BindingResult bindingResult) {
        log.info("[UserController.updateNickname]");
        if (bindingResult.hasErrors()) {
            throw new UserException(INVALID_USER_VALUE, getErrorMessages(bindingResult));
        }
        userService.updateNickname(userId, patchNicknameRequest.getNickname());
        return new BaseResponse<>(null);
    }

    /**
     * 회원 핸드폰 번호 수정
     */
    @PatchMapping("/{userId}/phoneNumber")
    public BaseResponse<String> updatePhoneNumber(@PathVariable long userId,
                                                  @Validated @RequestBody PatchPhoneNumberRequest patchPhoneNumberRequest, BindingResult bindingResult) {
        log.info("[UserController.updatePhoneNumber]");
        if (bindingResult.hasErrors()) {
            throw new UserException(INVALID_USER_VALUE, getErrorMessages(bindingResult));
        }
        userService.updatePhoneNumber(userId, patchPhoneNumberRequest.getPhoneNumber());
        return new BaseResponse<>(null);
    }

    /**
     * 회원 상태 수정
     */
    @PatchMapping("/{userId}/status")
    public BaseResponse<String> updateStatus(@PathVariable long userId,
                                             @Validated @RequestBody PatchStatusRequest patchStatusRequest, BindingResult bindingResult) {
        log.info("[UserController.updateStatus]");
        if (bindingResult.hasErrors()) {
            throw new UserException(INVALID_USER_VALUE, getErrorMessages(bindingResult));
        }
        userService.updateStatus(userId, patchStatusRequest.getStatus());
        return new BaseResponse<>(null);
    }

    /**
     * 회원 전체 정보 수정
     */
    @PutMapping("/{userId}")
    public BaseResponse<String> updateUserAllInfo(@PathVariable long userId,
                                                  @Validated @RequestBody PostUserRequest postUserRequest, BindingResult bindingResult) {
        log.info("[UserController.updateUserAllInfo]");
        if (bindingResult.hasErrors()) {
            throw new UserException(INVALID_USER_VALUE, getErrorMessages(bindingResult));
        }
        userService.updateUserAllInfo(userId, postUserRequest);
        return new BaseResponse<>(null);
    }

    /**
     * 전체 회원 조회
     */
    @GetMapping("")
    public BaseResponse<List<GetUserResponse>> getUsers(
            @RequestParam(required = false, defaultValue = "") String nickname,
            @RequestParam(required = false, defaultValue = "ACTIVE") String status) {
        log.info("[UserController.getUsers]");
        if (!status.equals("ACTIVE") && !status.equals("INACTIVE")) {
            throw new UserException(INVALID_USER_STATUS);
        }
        return new BaseResponse<>(userService.getUsers());
    }
}
