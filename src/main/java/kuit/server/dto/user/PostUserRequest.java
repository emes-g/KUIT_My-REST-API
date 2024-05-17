package kuit.server.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class PostUserRequest {
    private String nickname;
    private String phoneNumber;
    private String status;
}
