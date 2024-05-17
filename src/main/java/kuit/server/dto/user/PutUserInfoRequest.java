package kuit.server.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PutUserInfoRequest {
    private String nickname;
    private String phoneNumber;
    private String status;
}