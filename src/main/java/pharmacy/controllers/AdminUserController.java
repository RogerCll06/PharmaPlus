package pharmacy.controllers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pharmacy.dtos.auth.AuthResponseDTO;
import pharmacy.dtos.autorization.RoleChangeRequestDTO;
import pharmacy.dtos.autorization.RolesResponseDTO;
import pharmacy.dtos.roles.RoleDetailsDto;
import pharmacy.service.UserAccountService;
import pharmacy.service.UserRoleService;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Autorizacion", description = "Endpoints para manejo de roles")
public class AdminUserController {

    private final UserRoleService userRoleService;
    private final UserAccountService userAccountService;

    @Operation(summary = "Listar roles de usuario")
    @GetMapping("/{id}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RolesResponseDTO> getRoles(@PathVariable UUID id) {
        return ResponseEntity.ok(userRoleService.getUserRoles(id));
    }

    @Operation(summary = "Agregar un rol a un usuario")
    @PostMapping("/{id}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthResponseDTO> addRole(@PathVariable UUID id,
                                                   @Valid @RequestBody RoleChangeRequestDTO request) {
        return ResponseEntity.ok(userRoleService.addRoleToUser(id, request.role()));
    }

    @Operation(summary = "Quitar un rol a un usuario")
    @DeleteMapping("/{id}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthResponseDTO> deleteRole(@PathVariable UUID id,
                                                      @RequestBody RoleChangeRequestDTO requestDTO) {
        return ResponseEntity.ok(userRoleService.removeRoleFromUser(id, requestDTO.role()));
    }

    @Operation(summary = "Desactivar cuenta de usuario")
    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthResponseDTO> deactivate(@PathVariable UUID id,
                                                      @RequestParam(required = false) String reason,
                                                      Authentication authentication) {
        String admin = authentication != null ? authentication.getName() : "system";

        return ResponseEntity.ok(userAccountService.deactivateUser(id, reason, admin));
    }

    @Operation(summary = "Activar cuenta de usuario")
    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthResponseDTO> activate(@PathVariable UUID id,
                                                    Authentication authentication) {
        String admin = authentication != null ? authentication.getName() : "system";

        return ResponseEntity.ok(userAccountService.activateUser(id, admin));
    }


    @Operation(summary = "Obtener lista de roles con descripciones")
    @GetMapping("/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RoleDetailsDto>> getRoleDetails() {
        return ResponseEntity.ok(userRoleService.getRoleDetails());
    }


}
