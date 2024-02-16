package com.tepkeven.bankservices.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.tepkeven.bankservices.dto.UserRequestDto;
import com.tepkeven.bankservices.dto.UserResponseDto;
import com.tepkeven.bankservices.model.HttpItemResponse;
import com.tepkeven.bankservices.model.HttpItemsResponse;
import com.tepkeven.bankservices.service.admin.BUserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/admin/users")
@Tag(name = "Users API for Admin")
public class BUserController {
    
    private final BUserService backUserService;

    public BUserController(BUserService backUserService){
        this.backUserService = backUserService;
    }

    @GetMapping("")
    public ResponseEntity<HttpItemsResponse<UserResponseDto>> getUsers() throws Exception {

        HttpItemsResponse<UserResponseDto> responseResult = new HttpItemsResponse<UserResponseDto>(200, "Success", this.backUserService.getUsers());
        return new ResponseEntity<>(responseResult, HttpStatus.OK);
    }

    @GetMapping("{userId}")
    public ResponseEntity<HttpItemResponse<UserResponseDto>> getUser(@PathVariable("userId") Long userId) throws Exception {

        HttpItemResponse<UserResponseDto> responseResult = new HttpItemResponse<UserResponseDto>(200, "Success", this.backUserService.getUser(userId));
        return new ResponseEntity<>(responseResult, HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<HttpItemResponse<UserResponseDto>> addUser(@Valid @RequestBody UserRequestDto userDto) throws Exception {

        HttpItemResponse<UserResponseDto> responseResult = new HttpItemResponse<UserResponseDto>(200, "Success", this.backUserService.addUser(userDto));
        return new ResponseEntity<>(responseResult, HttpStatus.OK);
    }

    @PutMapping("edit/{userId}")
    public ResponseEntity<HttpItemResponse<UserResponseDto>> editUser(@PathVariable("userId") Long userId, @Valid @RequestBody UserRequestDto userDto) throws Exception {

        HttpItemResponse<UserResponseDto> responseResult = new HttpItemResponse<UserResponseDto>(200, "Success", this.backUserService.editUser(userId, userDto));
        return new ResponseEntity<>(responseResult, HttpStatus.OK);
    }

    @DeleteMapping("delete/{userId}")
    public ResponseEntity<HttpItemResponse<UserResponseDto>> deleteUser(@PathVariable("userId") Long userId) throws Exception {

        this.backUserService.deleteUser(userId);
        HttpItemResponse<UserResponseDto> responseResult = new HttpItemResponse<UserResponseDto>(200, "Success", null);
        return new ResponseEntity<>(responseResult, HttpStatus.OK);
    }


}
