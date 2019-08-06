package visloukh.libra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import visloukh.libra.Domain.ChapterDTO;
import visloukh.libra.Entity.ChapterEntity;
import visloukh.libra.Entity.RoleEntity;
import visloukh.libra.Entity.UserEntity;
import visloukh.libra.Repository.RoleRepository;
import visloukh.libra.Repository.UserRepository;
import visloukh.libra.Service.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@SpringBootApplication
public class LibraApplication implements CommandLineRunner {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BookService bookService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserDetailsService userDetailsService;
//    @Autowired
//    private ChapterService chapterService;

    public static void main(String[] args) {
        SpringApplication.run(LibraApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        if (roleService.findAllRoles().isEmpty()) {
            roleService.createDefaultRoles();
            userService.createDefaultUsers();
            categoryService.createDefaultCategorys();
            bookService.createDefaultBooks();
            bookService.createDefaultChapters();
            userDetailsService.createDefaultUserDetails();
        }

//        List<RoleEntity> roles = new ArrayList<>();
//        roles = roleService.findAllRoles();
//        for (RoleEntity role : roles) {
//            System.out.println(role.toString());
//        }
//        userAdmin=userService.findUserById(1).;
//        System.out.println(userAdmin.toString());
//        List<UserEntity> users = new ArrayList<>();
//        users= userService.findAllUser();
//        for (UserEntity user : users) {
//            System.out.println(user.toString());
//        }
//        List<UserEntity> users = new ArrayList<>();
//        users= userService.findAllUser();
//        for (UserEntity user: users) {
//            System.out.println(user);
//
//        }

    }
}
