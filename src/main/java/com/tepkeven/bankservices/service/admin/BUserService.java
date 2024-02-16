package com.tepkeven.bankservices.service.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tepkeven.bankservices.repository.UserRepository;
import com.tepkeven.bankservices.dto.UserRequestDto;
import com.tepkeven.bankservices.dto.UserResponseDto;
import com.tepkeven.bankservices.entity.Role;
import com.tepkeven.bankservices.entity.User;

@Service
public class BUserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public BUserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto UserToDto(User user){
        return new UserResponseDto(user.getFirstname(), user.getLastname(), user.getGender(), user.getAccounts());
    }

    public List<UserResponseDto> getUsers() throws Exception {
        return userRepository.findAll().stream().map(this::UserToDto).collect(Collectors.toList());
    } 

    public UserResponseDto getUser(Long userId){
        
        User user = userRepository.findById(userId).get();
        return UserToDto(user);
    }

    public UserResponseDto addUser(UserRequestDto userDto) throws Exception{
        
        User existedUser = userRepository.findByPhone(userDto.phone()).orElse(null);
        
        if(existedUser != null){

            throw new Exception("Phone number is already taken.");
        }

        User user = new User();
        user.setFirstname(userDto.firstname());
        user.setLastname(userDto.lastname());
        user.setGender(userDto.gender());
        user.setDob(userDto.dob());
        user.setPhone(userDto.phone());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setAddress(userDto.address());

        Role role = new Role();
        role.setId(userDto.roleId());
        user.setRole(role);

        User addedUser = userRepository.save(user);
        return UserToDto(addedUser);
    }

    public UserResponseDto editUser(Long userId, UserRequestDto userDto) throws Exception{

        User user = userRepository.findById(userId).get();
        
        user.setFirstname(userDto.firstname());
        user.setLastname(userDto.lastname());
        user.setGender(userDto.gender());
        user.setDob(userDto.dob());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setAddress(userDto.address());

        Role role = new Role();
        role.setId(userDto.roleId());
        user.setRole(role);

        User editedUser = userRepository.save(user);
        return UserToDto(editedUser);
    }

    public void deleteUser(Long userId) throws Exception {
        userRepository.deleteById(userId);
    }
}
