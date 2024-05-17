package kuit.server.controller;

import kuit.server.common.BaseResponse;
import kuit.server.common.exception.UserException;
import kuit.server.dto.user.PatchNicknameRequest;
import kuit.server.dto.user.PatchPhoneNumberRequest;
import kuit.server.dto.user.PostUserRequest;
import kuit.server.dto.user.PostUserResponse;
import kuit.server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
}
