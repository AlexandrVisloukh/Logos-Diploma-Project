
package visloukh.libra.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import visloukh.libra.Entity.RoleEntity;
import visloukh.libra.Entity.UserEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    RoleEntity findByRoleIgnoreCase(String role);
}