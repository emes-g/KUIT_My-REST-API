package kuit.server.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchStatusRequest {

    @NotNull(message = "status: {NotNull}")
    private String status;

}
