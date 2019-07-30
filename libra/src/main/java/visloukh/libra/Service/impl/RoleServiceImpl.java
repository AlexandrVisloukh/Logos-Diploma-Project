package visloukh.libra.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import visloukh.libra.Entity.RoleEntity;
import visloukh.libra.Repository.RoleRepository;
import visloukh.libra.Service.RoleService;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<RoleEntity> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public RoleEntity findRoleById(Integer id) {
        return roleRepository.findById(id).get();
    }

    @Override
    public RoleEntity fingRoleByName(String role) {
        return roleRepository.findByRoleIgnoreCase(role);
    }

    @Override
    public void createDefaultRoles() {
        RoleEntity roleAdmin = new RoleEntity();
        RoleEntity roleGuest = new RoleEntity();
        RoleEntity roleUser = new RoleEntity();
        RoleEntity roleAuthor = new RoleEntity();

        roleAdmin.setRole("Admin");
        roleAdmin.setCreateDate(new Date());
        roleAdmin.setStatus("default");
        roleRepository.save(roleAdmin);

        roleGuest.setRole("Guest");
        roleGuest.setCreateDate(new Date());
        roleGuest.setStatus("default");
        roleRepository.save(roleGuest);

        roleUser.setRole("User");
        roleUser.setCreateDate(new Date());
        roleUser.setStatus("default");
        roleRepository.save(roleUser);


        roleAuthor.setRole("Author");
        roleAuthor.setCreateDate(new Date());
        roleAuthor.setStatus("default");
        roleRepository.save(roleAuthor);

    }
}
