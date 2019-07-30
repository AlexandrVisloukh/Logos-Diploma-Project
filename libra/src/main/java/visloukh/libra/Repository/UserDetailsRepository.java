package visloukh.libra.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import visloukh.libra.Entity.UserDetailsEntity;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Integer> {

    boolean existsByUserId (Integer userId);
    UserDetailsEntity findByUserId (Integer userId);


}
