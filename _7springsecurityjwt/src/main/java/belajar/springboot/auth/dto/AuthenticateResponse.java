package belajar.springboot.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticateResponse {
    private String token;
}
