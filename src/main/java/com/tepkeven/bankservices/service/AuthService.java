package com.tepkeven.bankservices.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tepkeven.bankservices.dto.RegisterDto;
import com.tepkeven.bankservices.dto.UserResponseDto;
import com.tepkeven.bankservices.entity.Role;
import com.tepkeven.bankservices.entity.User;
import com.tepkeven.bankservices.repository.RoleRepository;
import com.tepkeven.bankservices.repository.UserRepository;

@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto UserToDto(User user){
        return new UserResponseDto(user.getFirstname(), user.getLastname(), user.getGender(), user.getAccounts());
    }

    public UserResponseDto register(RegisterDto registerDto) throws Exception {

        User existedUser = userRepository.findByPhone(registerDto.phone()).orElse(null);
        
        if(existedUser != null){

            throw new Exception("Phone number is already taken.");
        }

        User user = new User();
        user.setFirstname(registerDto.firstname());
        user.setLastname(registerDto.lastname());
        user.setGender(registerDto.gender());
        user.setDob(registerDto.dob());
        user.setPhone(registerDto.phone());
        user.setPassword(passwordEncoder.encode(registerDto.password()));
        user.setAddress(registerDto.address());

        Role role = roleRepository.findByName("USER").get();
        user.setRole(role);

        User addedUser = userRepository.save(user);
        return UserToDto(addedUser);
    }
}
