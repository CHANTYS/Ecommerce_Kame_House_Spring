package com.KameHouse.ecom.services.auth;


import com.KameHouse.ecom.dto.SignupRequest;
import com.KameHouse.ecom.dto.UserDto;
import com.KameHouse.ecom.entity.User;
import com.KameHouse.ecom.enums.UserRole;
import com.KameHouse.ecom.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto createUser(SignupRequest signupRequest){

        User user = new User();


        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPasword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setRole(UserRole.ADMIN);

        User createdUser = userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setId(createdUser.getId());


        return userDto;
    }

public Boolean hasUserWithEmail(String email){
        return userRepository.findFirstByEmail(email).isPresent();
    }

@PostConstruct
public void createAdminAccount(){
        User adminAccount = userRepository.findByRole(UserRole.ADMIN);
        if(null == adminAccount){
            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setRole(UserRole.ADMIN);
            user.setPasword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
        }
}


}
