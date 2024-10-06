package com.pharma_manager.MediLive.controller;

import com.pharma_manager.MediLive.dto.UserDto;
import com.pharma_manager.MediLive.service.UserService;
import com.pharma_manager.MediLive.config.UserDetailsServiceImpl;
import com.pharma_manager.MediLive.utils.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie;

import java.util.HashMap;
import java.util.Map;


// controller for public or not-logged-in users
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    public UserService userService;

    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    public UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    public JwtUtils jwtUtils;

    private final String HOME_VIEW_COUNT = "HOME_VIEW_COUNT";

    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.saveNewUser(userDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody UserDto userDto, HttpServletResponse response, HttpSession session) throws Exception {
        try {
            // authenticate user using spring security
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getUserName(), userDto.getPassWord())
            );
            // get user details
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userDto.getUserName());

            // generate token
            String jwtToken = jwtUtils.generateToken(userDetails.getUsername());
            Cookie jwtCookie = jwtUtils.createCookie(jwtToken);
            // Adding jwt token to the response
            response.addCookie(jwtCookie);
//            incrementSessionCount(session, HOME_VIEW_COUNT);
            Map<String, String> responseBody = new HashMap<>();
            System.out.println(userDetails.getAuthorities().iterator().next().toString());
            responseBody.put("token", jwtToken);
            responseBody.put("role", userDetails.getAuthorities().iterator().next().toString());

            return new ResponseEntity<>(responseBody, HttpStatus.OK);

//            return new ResponseEntity<>(jwtToken, HttpStatus.OK);

        }catch (Exception e) {
            throw new Exception("Incorrect Username or Password. " + e);
        }
    }

//    private void incrementSessionCount(HttpSession session, String attr) {
//        int homeViewCount = session.getAttribute(attr) == null ? 0 : (Integer) session.getAttribute(attr);
//        session.setAttribute(attr, homeViewCount += 1);
//    }


}
