package com.KameHouse.ecom.service.auth;


import com.KameHouse.ecom.dto.ChangePasswordDto;
import com.KameHouse.ecom.dto.SignupRequest;
import com.KameHouse.ecom.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface AuthService {

     UserDto createUser(SignupRequest signupRequest) throws Exception;

     Boolean hasUserWithEmail(String email);

     UserDto getUserById(Long userId);

     UserDto updateUser(UserDto userDto) throws IOException;

    ResponseEntity<?> updatePasswordById(ChangePasswordDto changePasswordDto);
}
