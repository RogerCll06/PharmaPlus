package pharmacy.service;

import pharmacy.dtos.auth.AuthResponseDTO;
import pharmacy.dtos.autorization.RolesResponseDTO;
import pharmacy.dtos.roles.RoleDetailsDto;
import java.util.UUID;
import java.util.List;

public interface UserRoleService {
    RolesResponseDTO getUserRoles(UUID userId);
    AuthResponseDTO addRoleToUser(UUID userId, String roleName);
    AuthResponseDTO removeRoleFromUser(UUID userId, String roleName);
    //RoleStatisticsDto getUserStatistics();
    List<RoleDetailsDto> getRoleDetails();
}
