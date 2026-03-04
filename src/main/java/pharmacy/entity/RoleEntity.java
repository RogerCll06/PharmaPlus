package pharmacy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pharmacy.config.audit.Audit;
import pharmacy.config.audit.AuditListener;

import java.util.UUID;

@Entity
@Table(name = "roles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditListener.class)
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true, nullable = false)
    private String name;
    private String description;
    @Embedded
    private Audit audit;
}
