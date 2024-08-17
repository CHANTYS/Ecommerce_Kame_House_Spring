package com.KameHouse.ecom.services.auth;

import com.KameHouse.ecom.dto.SignupRequest;
import com.KameHouse.ecom.dto.UserDto;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);

    Boolean hasUserWithEmail(String email);
}
