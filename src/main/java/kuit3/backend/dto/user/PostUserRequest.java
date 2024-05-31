package kuit3.backend.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
public class PostUserRequest {

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
