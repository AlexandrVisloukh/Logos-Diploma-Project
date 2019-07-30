package visloukh.libra.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import visloukh.libra.Entity.RoleEntity;
import visloukh.libra.Entity.UserEntity;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <UserEntity,Integer> {

    boolean existsByEmail (String email);
    //List<UserEntity> findAllByRoles (RoleEntity role);
}
