package visloukh.libra.Controller;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import visloukh.libra.Domain.UserDTO;
import visloukh.libra.Domain.UserDetailsDTO;
import visloukh.libra.Entity.UserEntity;
import visloukh.libra.Repository.UserRepository;
import visloukh.libra.Service.FileStorageService;
import visloukh.libra.Service.UserDetailsService;
import visloukh.libra.Service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private FileStorageService fileStorageService;

    public final String SEPARATOR = System.getProperty("file.separator");
    @PostMapping
    public ResponseEntity<?> createUser(
            @RequestBody UserDTO userDTO) {

        userService.createUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(
                userService.findAllUser(), HttpStatus.OK
        );
    }

    @DeleteMapping("id={userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("id={userId}")
    public ResponseEntity<?> updateUser(
            @PathVariable("userId") Integer id,
            @RequestBody UserDTO userDTO) {
        userService.updateUser(userDTO,id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("id={userId}")
    public ResponseEntity<?> findUserById(@PathVariable("userId") Integer id) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);

    }
    @GetMapping("id={userId}/details")
    public ResponseEntity<?> findUserDetailsByUserId(@PathVariable("userId") Integer id) {

        return new ResponseEntity<>(userDetailsService.getDetailsByUserId(id), HttpStatus.OK);

    }
    @PostMapping("id={userId}/details")
    public ResponseEntity<?> createUserDetails(
            @PathVariable("userId") Integer userId,
            @Valid @RequestBody UserDetailsDTO userDetailsDTO) {
        System.out.println(userDetailsDTO.toString());
        System.out.println(userId);
        userDetailsService.createUserDetails(userDetailsDTO, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("id={userId}/details")
    public ResponseEntity<?> updateUserDetails(
            @PathVariable("userId") Integer userId,
            @Valid @RequestBody UserDetailsDTO userDetailsDTO) {

        userDetailsService.redactUserDetails(userDetailsDTO, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("page") // /users/page?page=0&size=20&sort=
    public ResponseEntity<?> getUsersByPage(@PageableDefault Pageable pageable) {
        return new ResponseEntity<>(userService.getUsersByPage(pageable), HttpStatus.OK);
    }

    @PostMapping("{userId}/image")
    public ResponseEntity<?> uploadImage(
            @PathVariable("userId") int id,
            @RequestParam("imageFile") MultipartFile file
    ) {
        System.out.println(file.getOriginalFilename());

        String newFileName= fileStorageService.storeFile(file,id,"users");
        userService.addImageToUser(id, newFileName);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("image") // users/image?imageName=....
    public ResponseEntity<?> getImage(
            @RequestParam("imageName") String name,
            HttpServletRequest servletRequest
    ) {

        Resource resource = fileStorageService.loadFile(name,"users");

        //     String contentType = null;
//
//        try {
//            contentType = servletRequest
//                            .getServletContext()
//                                .getMimeType(
//                                        resource.getFile().getAbsolutePath());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (contentType == null) {
//            contentType = "application/octet-stream";
//        }
        return ResponseEntity.ok()
                //   .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
