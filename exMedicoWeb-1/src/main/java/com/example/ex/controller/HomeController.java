package com.example.ex.controller;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex.AuthRequest;
import com.example.ex.JwtUtils;
import com.example.ex.UserPrincipal;



@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class HomeController {
	
	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest loginRequest) {

    	Authentication authentication = authenticationManager
    	        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    	    SecurityContextHolder.getContext().setAuthentication(authentication);

    	    UserPrincipal userDetails = (UserPrincipal) userDetailsService.loadUserByUsername(loginRequest.getUsername());

    	    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

    	    if(userDetails.getSpecializzazione()!=null) {
    	    	return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
    	    	        .body(new UserPrincipal(userDetails.getId(),
    	    	        		userDetails.getNome(),
    	    	        		userDetails.getEmail(),
    	    	        		userDetails.getPassword(),
    	    	        		userDetails.getSpecializzazione(),
    	    	        		userDetails.getAppuntamenti(),
    	    	        		userDetails.getAuthorities()));
    	    }else {
    	    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
    	        .body(new UserPrincipal(userDetails.getId(),
    	        		userDetails.getNome(),
    	        		userDetails.getEmail(),
    	        		userDetails.getPassword(),
    	        		userDetails.getAppuntamenti(),
    	        		userDetails.getAuthorities()));
    	    }
    }

}
