package pharmacy.service.Impl;


import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pharmacy.dtos.auth.AuthResponseDTO;
import pharmacy.dtos.autorization.RolesResponseDTO;
import pharmacy.dtos.roles.RoleDetailsDto;
import pharmacy.entity.RoleEntity;
import pharmacy.entity.UserEntity;
import pharmacy.repository.RoleRepository;
import pharmacy.repository.UserRepository;
import pharmacy.service.UserRoleService;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public RolesResponseDTO getUserRoles(UUID userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow();
        Set<String> roles = user.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toSet());
        return new RolesResponseDTO(userId, roles);
    }

    @Override
    @Transactional
    public AuthResponseDTO addRoleToUser(UUID userId, String roleName) {
        UserEntity user = userRepository.findById(userId).orElseThrow();

        RoleEntity role = roleRepository.findByName(roleName).orElseThrow();
        if (user.getRoles() == null) {
            user.setRoles(Set.of(role));
        } else if (user.getRoles().stream().noneMatch(r -> r.getName().equals(roleName))) {
            HashSet<RoleEntity> mutable = new HashSet<>(user.getRoles());
            mutable.add(role);
            user.setRoles(mutable);
        }
        userRepository.save(user);

        return new AuthResponseDTO(true, "Rol agregado", Instant.now());
    }

    @Override
    @Transactional
    public AuthResponseDTO removeRoleFromUser(UUID userId, String roleName) {
        UserEntity user = userRepository.findById(userId).orElseThrow();
        if (user.getRoles() == null || user.getRoles().stream().noneMatch(r -> r.getName().equals(roleName))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario no tiene ese rol");

        }

        var mutable = new HashSet<>(user.getRoles());
        mutable.removeIf(r -> r.getName().equals(roleName));
        user.setRoles(mutable);
        userRepository.save(user);
        return new AuthResponseDTO(true, "Rol removido", Instant.now());
    }


    @Override
    public List<RoleDetailsDto> getRoleDetails() {
        return roleRepository.findAll().stream()
                .map(role -> new RoleDetailsDto(role.getName(), role.getDescription()))
                .collect(Collectors.toList());
    }
}
