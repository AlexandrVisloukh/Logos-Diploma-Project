package visloukh.libra.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import visloukh.libra.Domain.UserDTO;
import visloukh.libra.Entity.UserEntity;
import visloukh.libra.Service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("signup")
public class SignupController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(
           @Valid @RequestBody UserDTO userDTO) {

        userService.createUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}