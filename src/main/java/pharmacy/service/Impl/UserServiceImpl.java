package pharmacy.service.Impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pharmacy.dtos.autorization.UserDTO;

import pharmacy.mapper.UserMapper;
import pharmacy.service.UserService;
import pharmacy.repository.UserRepository;
import pharmacy.entity.UserEntity;


import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    @Override
    public UserDTO getUserById(UUID id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado con id: " + id));
        return userMapper.toDTO(user);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDTO getUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no enconrado con email " + email));
        return userMapper.toDTO(user);
    }
}

