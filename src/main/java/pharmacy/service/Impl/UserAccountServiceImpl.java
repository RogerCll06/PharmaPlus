package pharmacy.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import pharmacy.config.audit.Audit;
import pharmacy.dtos.auth.AuthResponseDTO;
import pharmacy.entity.UserEntity;
import pharmacy.repository.UserRepository;
import pharmacy.service.UserAccountService;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserAccountServiceImpl implements UserAccountService {

    private final UserRepository userRepository;


    @Override
    public AuthResponseDTO deactivateUser(UUID userId, String reason, String admin) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (!Boolean.TRUE.equals(user.getEnabled()))
            return new AuthResponseDTO(false, "Usuario ya esta desactivado", Instant.now());

        Audit a = user.getAudit();
        if (a == null) {
            a = Audit.builder().build();
        }
        a.setUpdatedBy(admin);
        a.setUpdatedAt(Instant.now());
        a.setStatusReason(reason);
        user.setAudit(a);
        user.setEnabled(false);

        userRepository.save(user);


        return new AuthResponseDTO(true, "Usuario desactivado", Instant.now());

    }

    @Override
    public AuthResponseDTO activateUser(UUID userId, String admin) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (Boolean.TRUE.equals(user.getEnabled())) {
            return new AuthResponseDTO(false, "Usuario ya esta activo", Instant.now());
        }

        Audit a = user.getAudit();
        if (a == null) {
            a = Audit.builder().build();
        }
        a.setUpdatedBy(admin);
        a.setUpdatedAt(Instant.now());
        a.setStatusReason(null);
        user.setAudit(a);
        user.setEnabled(true);

        userRepository.save(user);
        return new AuthResponseDTO(true, "Usuario activado", Instant.now());
    }
}