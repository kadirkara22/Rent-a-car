 package com.kadirkara.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkara.dto.AuthRequest;
import com.kadirkara.dto.AuthResponse;
import com.kadirkara.dto.DtoCustomer;
import com.kadirkara.service.AuthenticationService;

import jakarta.validation.Valid;

@RestController
public class RestAuthenticationContoller extends RestBaseController{

	private final AuthenticationService authenticationService;
	
	public RestAuthenticationContoller(AuthenticationService authenticationService){
		this.authenticationService=authenticationService;
	}
	
	@PostMapping("/register")
    public RootEntity<AuthResponse> register(@Valid @RequestBody AuthRequest request) {
        return ok(authenticationService.register(request));
    }
	
	@PostMapping("/login")
	    public RootEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
	        AuthResponse response = authenticationService.login(request);
	        return RootEntity.ok(response);
	    }
	
	@PostMapping("/login-admin")
    public RootEntity<AuthResponse> loginAdmin(@Valid @RequestBody AuthRequest request) {
        AuthResponse response = authenticationService.loginAdmin(request);
        return RootEntity.ok(response);
    }
	/*@PostMapping("/validate-token")
    public RootEntity<?> validateToken(@RequestBody TokenRequest tokenRequest) {
        boolean isValid = authenticationService.validateToken(tokenRequest.getToken());
        return RootEntity.ok(isValid);
    }*/
}
