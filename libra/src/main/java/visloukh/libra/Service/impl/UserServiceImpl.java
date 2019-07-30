package visloukh.libra.Service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import visloukh.libra.Domain.UserDTO;
import visloukh.libra.Entity.RoleEntity;
import visloukh.libra.Entity.UserEntity;
import visloukh.libra.Repository.RoleRepository;
import visloukh.libra.Repository.UserRepository;
import visloukh.libra.Service.RoleService;
import visloukh.libra.Service.UserService;
import visloukh.libra.Utils.ObjectMapperUtils;
import visloukh.libra.exceptions.AlreadyExistsException;
import visloukh.libra.exceptions.NotFoundException;
import visloukh.libra.exceptions.ServerException;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ObjectMapperUtils modelMapper;

    @Override
    public void createUser(UserEntity userEntity) {
        if (existUser(userEntity.getEmail())) {
            throw new AlreadyExistsException("user with this email: " + userEntity.getEmail() + " already exist");
        }
        userEntity.setCreateDate(new Date());
        userEntity.setStatus("created");
        userRepository.save(userEntity);
       // System.out.println("Saved to base: "+ userEntity.toString());
    }

    @Override
    public void createUser(UserDTO userDTO) {
        System.out.println(userDTO);
        System.out.println(userDTO.getPassword());
        System.out.println(userDTO.getConfirmPassword());
        if (userDTO.getPassword().equals(userDTO.getConfirmPassword())) {

            UserEntity user = modelMapper.map(userDTO,UserEntity.class);
            System.out.println("user password="+ user.getPassword());
//            UserEntity user = new UserEntity();
//            user.setName(userDTO.getName());
//            user.setEmail(userDTO.getEmail());
//            user.setPassword(userDTO.getPassword());
//            user.setSexType(userDTO.getSexType());
//            user.setImage(userDTO.getImage());
            createUser(user);
        }  else {
                    throw new ServerException("password"+userDTO.getPassword()+" not equals to "+ userDTO.getConfirmPassword());
                }

    }
//    @Override
//    public List<UserEntity> findUsersByRole(RoleEntity roleEntity) {
//
//        return ;
//    }

    @Override
    public UserEntity findUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(
                    ()->  new NotFoundException("user with id=["+id+"] not exist"));

    }

    @Override
    public List<UserEntity> findAllUser() {
        return userRepository.findAll();
        //return modelMaper.mapAll(userRepository.findAll(), UserDTO.class);
    }

    @Override
    public boolean existUser(Integer id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean existUser(String email) {
        return userRepository.existsByEmail(email.toLowerCase());
    }

    @Override
    public void deleteUser(Integer id) {
        UserEntity user= userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with this id "+id+ " not found"));
        user.setStatus("deleted");
        user.setRedactDate(new Date());
        userRepository.save(user);
    }

    @Override
    public void deleteUserFromDB(Integer id) {
        if (!existUser(id)){
            throw new NotFoundException("User with this id "+id+ " not found");}
        userRepository.deleteById(id);
    }

    @Override
    public void createDefaultUsers() {
        UserEntity userAdmin = new UserEntity();
        userAdmin.setName("admin");
        userAdmin.setEmail("admin@gmail.com");
        userAdmin.setStatus("created");
        userAdmin.setPassword("12345");
        userAdmin.setRoles(roleService.findAllRoles());
        userAdmin.setSexType("male");
        userAdmin.setCreateDate(new Date());
        createUser(userAdmin);


        for (int i=2; i<150; i++) {
            UserEntity user=new UserEntity();
            user.setName("User"+i);
            user.setPassword("1234");
            user.setEmail("defaultEmail"+i+"@gmail.com");
           // user.setImage("user"+i+".png");
            //addRole(i,"User");
            user.getRoles().add(roleService.fingRoleByName("User"));
            user.setSexType("male");
            //System.out.println(user.toString());
            createUser(user);
        }


    }



    @Override
    public UserEntity updateUser(UserDTO userDTO, Integer id) {
        UserEntity currentUser= userRepository.findById(id).orElseThrow(()-> new NotFoundException("User with this id="+id+ " not exist"));
       // System.out.println("Updated user: "+ currentUser.toString());
        UserEntity userToUpdate = modelMapper.map(userDTO,UserEntity.class);
        currentUser.setStatus("updated");
        currentUser.setRedactDate(new Date());
        currentUser.setName(userToUpdate.getName());
        currentUser.setEmail(userToUpdate.getEmail());
        if(existUser(currentUser.getEmail())) {
            throw new AlreadyExistsException("User with this email="+currentUser.getEmail()+ " already exist");
        }

        currentUser.setPassword(userToUpdate.getPassword());
        currentUser.setSexType(userToUpdate.getSexType());
        if (userToUpdate.getImage()!=null)
            currentUser.setImage(userToUpdate.getImage());
//        currentUser.setRoles(userToUpdate.getRoles());
       userRepository.save(currentUser);
       // System.out.println("Updated to: "+ currentUser.toString()) ;
            return currentUser;
    }

    @Override
    public UserEntity addRole(Integer userID, String  roleName) {
        UserEntity user = userRepository.findById(userID).orElseThrow(()-> new NotFoundException("User with this id "+userID+ " not found"));
        RoleEntity role = roleService.fingRoleByName(roleName);
        user.getRoles().add(role);
        return user;
    }

    @Override
    public UserEntity removeRole(Integer userID, String roleName) {
        UserEntity user = userRepository.findById(userID).orElseThrow(()-> new NotFoundException("User with this id "+userID+ " not found"));
        RoleEntity role = roleService.fingRoleByName(roleName);
        user.getRoles().remove(role.getId()-1);
        return user;
    }

    @Override
    public Page<UserEntity> getUsersByPage(Pageable pageable) {
        Page<UserEntity> userEntities =
                userRepository.findAll(pageable);
        // page = 0
        // size = 10
        return userEntities;
    }

    @Override
    public void addImageToUser(int id, String fileName) {
        UserEntity userEntity =
                userRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("User not found"));
        userEntity.setImage(fileName);
        userRepository.save(userEntity);
    }
}
