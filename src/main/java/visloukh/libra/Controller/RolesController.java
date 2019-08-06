package visloukh.libra.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import visloukh.libra.Service.RoleService;

@RestController
@RequestMapping("roles")
public class RolesController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<?> getAllRoles() {
        return new ResponseEntity<>(
                roleService.findAllRoles(), HttpStatus.OK
        );
    }

    @GetMapping("{roleName}")
    public ResponseEntity<?> findUserById(@PathVariable("roleName") String roleName) {
        return new ResponseEntity<>(roleService.fingRoleByName(roleName), HttpStatus.OK);

    }


}
