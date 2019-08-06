package visloukh.libra.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import visloukh.libra.Domain.UserDetailsDTO;
import visloukh.libra.Entity.UserDetailsEntity;
import visloukh.libra.Entity.UserEntity;
import visloukh.libra.Repository.UserDetailsRepository;
import visloukh.libra.Service.UserDetailsService;
import visloukh.libra.Service.UserService;
import visloukh.libra.Utils.ObjectMapperUtils;
import visloukh.libra.exceptions.NotFoundException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService {
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapperUtils modelMapper;

    @Override
    public void createUserDetails(UserDetailsEntity userDetails, Integer userId) {

            userDetails.setUser(userService.findUserById(userId));
            userDetails.setCreateDate(new Date());
            userDetails.setStatus("created");
            userDetailsRepository.save(userDetails);

    }
    public void createUserDetails(UserDetailsDTO userDetails, Integer userId) {
        UserDetailsEntity userDetailsEntity= modelMapper.map(userDetails,UserDetailsEntity.class);
        createUserDetails(userDetailsEntity,userId);
    }

    @Override
    public boolean existUserDetailsByUserId(Integer userId) {
        if (userService.existUser(userId)) {
            return userDetailsRepository.existsByUserId(userId);
        }else throw new NotFoundException("user with this id is not exist");
    }

    @Override
    public void createDefaultUserDetails() {
        UserDetailsEntity userDetails1 = new UserDetailsEntity();
        UserDetailsEntity userDetails2 = new UserDetailsEntity();
        UserDetailsEntity userDetails3 = new UserDetailsEntity();
        UserDetailsEntity userDetails4 = new UserDetailsEntity();
        UserDetailsEntity userDetails5 = new UserDetailsEntity();
        userDetails1.setFirstName("Alex");
        userDetails1.setLastName("Ivanov");
        userDetails2.setFirstName("Oleg");
        userDetails2.setLastName("Ivanov");
        userDetails3.setFirstName("Max");
        userDetails3.setLastName("Ivanov");
        userDetails4.setFirstName("Anna");
        userDetails4.setLastName("Sidorov");
        userDetails5.setFirstName("Natasha");
        userDetails5.setLastName("Sidorov");
        userDetails1.setUser(userService.findUserById(1));
        userDetails2.setUser(userService.findUserById(2));
        userDetails3.setUser(userService.findUserById(3));
        userDetails4.setUser(userService.findUserById(4));
        userDetails5.setUser(userService.findUserById(5));

        try {
            SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");

            userDetails1.setBirthDay( formater.parse("31/12/1998"));
            userDetails2.setBirthDay( formater.parse("19/10/1995"));
            userDetails3.setBirthDay( formater.parse("05/04/1993"));
            userDetails4.setBirthDay( formater.parse("24/05/1992"));
            userDetails5.setBirthDay( formater.parse("23/01/1988"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        createUserDetails(userDetails1,2);
        createUserDetails(userDetails2,3);
        createUserDetails(userDetails3,4);
        createUserDetails(userDetails4,5);
        createUserDetails(userDetails5,6);


    }

    @Override
    public UserDetailsEntity getDetailsByUserId(Integer userId) {
        if (existUserDetailsByUserId(userId)) {
            return userDetailsRepository.findByUserId(userId);
        } else System.out.println("details on this user id= " + userId + " are not exist");
        return null;
    }
    @Override
    public UserDetailsEntity getDetailsById(Integer id) {
        return userDetailsRepository.findById(id).get();
    }

    @Override
    public List<UserDetailsEntity> getAllUserDetails() {
        return userDetailsRepository.findAll();
    }

    @Override
    public boolean existUserDetails(Integer id) {
        return userDetailsRepository.existsById(id);
    }


    @Override
    public boolean redactUserDetails(UserDetailsDTO userDetailsToRedact, Integer userId) {
        UserDetailsEntity userDetailsEntity=modelMapper.map(userDetailsToRedact,UserDetailsEntity.class);
        return redactUserDetails(userDetailsEntity,userId);

    }
    @Override
    public boolean redactUserDetails(UserDetailsEntity userDetailsToRedact, Integer userId) {
        if (existUserDetailsByUserId(userId)) {
            UserDetailsEntity currentUserDetails = userDetailsRepository.findByUserId(userId);
          // System.out.println("you are redacted user details with id=" + id);
           // System.out.println(currentUserDetails.toString());
            currentUserDetails.setStatus("updated");
            currentUserDetails.setRedactDate(new Date());
            currentUserDetails.setFirstName(userDetailsToRedact.getFirstName());
            currentUserDetails.setLastName(userDetailsToRedact.getLastName());
            currentUserDetails.setBirthDay(userDetailsToRedact.getBirthDay());
            currentUserDetails.setAboutMe(userDetailsToRedact.getAboutMe());
            currentUserDetails.setLocation(userDetailsToRedact.getLocation());
            currentUserDetails.setContactInfo(userDetailsToRedact.getContactInfo());
            userDetailsRepository.save(currentUserDetails);
         //   System.out.println("updated to:\n" + currentUserDetails.toString());
            return true;
        }else {
            System.out.println("UserDetails with this id is not exist");
            return false;
        }
    }
}
