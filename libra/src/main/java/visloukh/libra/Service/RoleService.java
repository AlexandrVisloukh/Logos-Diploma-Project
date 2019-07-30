package visloukh.libra.Service;

import visloukh.libra.Entity.RoleEntity;

import java.util.List;

public interface RoleService {

    void createDefaultRoles ();
    List<RoleEntity> findAllRoles();
    RoleEntity findRoleById(Integer id);
    RoleEntity fingRoleByName(String role);
 }

