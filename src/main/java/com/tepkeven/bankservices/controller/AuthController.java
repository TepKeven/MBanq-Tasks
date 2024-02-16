package com.tepkeven.bankservices.controller;

import org.springframework.web.bind.annotation.RestController;

import com.tepkeven.bankservices.dto.LoginDto;
import com.tepkeven.bankservices.dto.RegisterDto;
import com.tepkeven.bankservices.model.HttpMessageResponse;
import com.tepkeven.bankservices.service.AuthService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Page")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository(); 
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    public AuthController(AuthService authService, AuthenticationManager authenticationManager){
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }
    
    @PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto) throws Exception{
        return new ResponseEntity<>(authService.register(registerDto), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<HttpMessageResponse> login(HttpServletRequest request, HttpServletResponse response, @Valid @RequestBody LoginDto loginDto) throws Exception{
        
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.phone(), loginDto.password()));

        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication); 
        securityContextHolderStrategy.setContext(context);
        securityContextRepository.saveContext(context, request, response); 

        HttpMessageResponse responseResult = new HttpMessageResponse(200, "Login Successfully", null);

        return new ResponseEntity<>(responseResult, HttpStatus.OK);
    }
    
}
