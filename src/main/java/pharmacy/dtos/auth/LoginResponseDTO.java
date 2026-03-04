package pharmacy.dtos.auth;

import pharmacy.dtos.autorization.UserDTO;

import java.time.Instant;

public class LoginResponseDTO {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Instant expiresAt;
    private String scope;
    private UserDTO user;
    private String message;

}

