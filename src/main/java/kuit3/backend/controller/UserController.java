package kuit3.backend.controller;

import kuit3.backend.common.argument_resolver.PreAuthorize;
import kuit3.backend.common.response.BaseResponse;
import kuit3.backend.common.exception.UserException;
import kuit3.backend.service.UserService;
import kuit3.backend.dto.user.*;
import kuit3.backend.util.BindingResultUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.INVALID_USER_VALUE;

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
            throw new UserException(INVALID_USER_VALUE, BindingResultUtils.getErrorMessages(bindingResult));
        }
        return new BaseResponse<>(userService.signUp(postUserRequest));
    }

    /**
     * 회원 닉네임 변경
     *
     * @param userId               회원 ID
     * @param patchNicknameRequest 닉네임 변경 요청
     * @param bindingResult        Validation 오류 보관 객체
     * @return 예외 혹은 응답
     */
    @PatchMapping("/{userId}/nickname")
    public BaseResponse<String> updateNickname(@PreAuthorize long userId,
                                               @Validated @RequestBody PatchNicknameRequest patchNicknameRequest, BindingResult bindingResult) {
        log.info("[UserController.updateNickname]");
        if (bindingResult.hasErrors()) {
            throw new UserException(INVALID_USER_VALUE, BindingResultUtils.getErrorMessages(bindingResult));
        }
        userService.updateNickname(userId, patchNicknameRequest.getNickname());
        return new BaseResponse<>(null);
    }

    /**
     * 회원 핸드폰 번호 수정
     */
    @PatchMapping("/{userId}/phoneNumber")
    public BaseResponse<String> updatePhoneNumber(@PreAuthorize long userId,
                                                  @Validated @RequestBody PatchPhoneNumberRequest patchPhoneNumberRequest, BindingResult bindingResult) {
        log.info("[UserController.updatePhoneNumber]");
        if (bindingResult.hasErrors()) {
            throw new UserException(INVALID_USER_VALUE, BindingResultUtils.getErrorMessages(bindingResult));
        }
        userService.updatePhoneNumber(userId, patchPhoneNumberRequest.getPhoneNumber());
        return new BaseResponse<>(null);
    }

    /**
     * 회원 상태 수정
     */
    @PatchMapping("/{userId}/status")
    public BaseResponse<String> updateStatus(@PreAuthorize long userId,
                                             @Validated @RequestBody PatchStatusRequest patchStatusRequest, BindingResult bindingResult) {
        log.info("[UserController.updateStatus]");
        if (bindingResult.hasErrors()) {
            throw new UserException(INVALID_USER_VALUE, BindingResultUtils.getErrorMessages(bindingResult));
        }
        userService.updateStatus(userId, patchStatusRequest.getStatus());
        return new BaseResponse<>(null);
    }

    /**
     * 회원 전체 정보 수정
     */
    @PutMapping("/{userId}")
    public BaseResponse<String> updateUserAllInfo(@PreAuthorize long userId,
                                                  @Validated @RequestBody PostUserRequest postUserRequest, BindingResult bindingResult) {
        log.info("[UserController.updateUserAllInfo]");
        if (bindingResult.hasErrors()) {
            throw new UserException(INVALID_USER_VALUE, BindingResultUtils.getErrorMessages(bindingResult));
        }
        userService.updateUserAllInfo(userId, postUserRequest);
        return new BaseResponse<>(null);
    }

    /**
     * 전체 회원 조회
     */
    @GetMapping("")
    public BaseResponse<List<GetUserResponse>> getAllUsers() {
        log.info("[UserController.getAllUsers]");
        return new BaseResponse<>(userService.getAllUsers());
    }

    /**
     * 해당 회원 조회
     */
    @GetMapping("/{userId}")
    public BaseResponse<List<GetUserResponse>> getUserByUserId(@PreAuthorize long userId){
        log.info("[UserController.getUser");
        return new BaseResponse<>(userService.getUserByUserId(userId));
    }
}
