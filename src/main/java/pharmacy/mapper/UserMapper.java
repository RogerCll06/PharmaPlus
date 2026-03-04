package pharmacy.mapper;

import pharmacy.config.MapStructConfig;
import pharmacy.entity.UserEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pharmacy.dtos.autorization.UserDTO;

@Mapper(config = MapStructConfig.class, uses = {RoleMapper.class})
public interface UserMapper {
    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "provider", expression = "java(userEntity.getProvider() != null ? userEntity.getProvider().name() : null)")
    UserDTO toDTO(UserEntity userEntity);


    @Mapping(target = "password", ignore = true)
    @Mapping(target = "accountNonExpired", ignore = true)
    @Mapping(target = "accountNonLocked", ignore = true)
    @Mapping(target = "credentialsNonExpired", ignore = true)
    UserEntity toEntity(UserDTO userDTO);
}



