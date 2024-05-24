package kuit.server.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
public class PutUserInfoRequest {

    @Length(max = 50, message = "최대 {max}자리까지 가능합니다")
    @NotBlank(message = "닉네임을 입력해주세요")
    private String nickname;

    @Length(max = 13, message = "최대 {max}자리까지 가능합니다")
    @NotBlank(message = "전화번호를 입력해주세요")
    private String phoneNumber;

    @Length(max = 50, message = "최대 {max}자리까지 가능합니다")
    @NotBlank(message = "상태를 입력해주세요")
    private String status;

}