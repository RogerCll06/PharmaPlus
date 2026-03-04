package pharmacy.service;

import pharmacy.dtos.autorization.UserDTO;

import java.util.UUID;

public interface UserService {
    UserDTO getUserById(UUID id);
    UserDTO getUserByEmail(String email);

}
