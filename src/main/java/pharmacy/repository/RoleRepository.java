package pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pharmacy.entity.RoleEntity;
import pharmacy.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, UUID>{
    Optional<RoleEntity> findByName(String name);
}
