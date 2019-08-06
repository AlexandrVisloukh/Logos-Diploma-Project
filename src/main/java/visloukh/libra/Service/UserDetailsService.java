package visloukh.libra.Service;

import visloukh.libra.Domain.UserDetailsDTO;
import visloukh.libra.Entity.UserDetailsEntity;

import java.util.List;

public interface UserDetailsService {

    void createUserDetails(UserDetailsEntity userDetails, Integer userId);
    void createUserDetails(UserDetailsDTO userDetails, Integer userId);
    boolean existUserDetails(Integer id);
    boolean existUserDetailsByUserId(Integer userId);

    UserDetailsEntity getDetailsByUserId (Integer id);

    void createDefaultUserDetails();

    UserDetailsEntity getDetailsById (Integer id);

    List<UserDetailsEntity> getAllUserDetails();

   boolean redactUserDetails(UserDetailsDTO userDetailsToRedact, Integer redactedId);
    boolean redactUserDetails(UserDetailsEntity userDetailsToRedact, Integer redactedId);
}
