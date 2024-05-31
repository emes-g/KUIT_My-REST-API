package kuit3.backend.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class PatchPhoneNumberRequest {

    @Length(max = 13, message = "최대 {max}자리까지 가능합니다")
    @NotBlank(message = "전화번호를 입력해주세요")
    private String phoneNumber;

}