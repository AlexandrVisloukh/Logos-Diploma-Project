package visloukh.libra.Service;

import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import visloukh.libra.Domain.UserDTO;
import visloukh.libra.Entity.RoleEntity;
import visloukh.libra.Entity.UserEntity;

import java.util.List;

public interface UserService {

    void createUser (UserEntity userEntity);
    void createUser (UserDTO userDTO);


    void createDefaultUsers();

    UserEntity findUserById(Integer id);

    List<UserEntity> findAllUser();

   // List<UserEntity> findUsersByRole(RoleEntity roleEntity);

    boolean existUser (Integer id);
    boolean existUser (String email);
    void deleteUser (Integer id);
    void deleteUserFromDB (Integer id);
    UserEntity updateUser (UserDTO userDTO, Integer id);

    UserEntity addRole (Integer userID, String role);
    UserEntity removeRole (Integer userID, String role);

    Page<UserEntity> getUsersByPage(Pageable pageable);

    void addImageToUser(int id, String fileName);


}
