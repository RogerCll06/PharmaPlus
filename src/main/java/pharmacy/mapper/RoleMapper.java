package pharmacy.mapper;

import org.mapstruct.Mapper;
import pharmacy.config.MapStructConfig;
import pharmacy.dtos.autorization.RoleDTO;
import pharmacy.entity.RoleEntity;

@Mapper(config = MapStructConfig.class)
public interface RoleMapper {
    RoleDTO toDTO(RoleEntity roleEntity);

    RoleEntity toEntity(RoleDTO roleDTO);
}
