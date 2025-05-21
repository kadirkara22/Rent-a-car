package com.kadirkara.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kadirkara.dto.AuthRequest;
import com.kadirkara.dto.AuthResponse;
import com.kadirkara.dto.DtoAccount;
import com.kadirkara.dto.DtoAddress;
import com.kadirkara.dto.DtoCustomer;
import com.kadirkara.dto.DtoCustomerIU;
import com.kadirkara.dto.DtoUser;
import com.kadirkara.dto.RefreshTokenRequest;
import com.kadirkara.enums.Role;
import com.kadirkara.exception.BaseException;
import com.kadirkara.exception.ErrorMessage;
import com.kadirkara.exception.MessageType;
import com.kadirkara.jwt.JWTService;
import com.kadirkara.model.Account;
import com.kadirkara.model.Address;
import com.kadirkara.model.Customer;
import com.kadirkara.model.RefreshToken;
import com.kadirkara.model.Transaction;
import com.kadirkara.model.User;
import com.kadirkara.repository.CustomerRepository;
import com.kadirkara.repository.RefreshTokenRepository;
import com.kadirkara.repository.UserRepository;
@Service
public class AuthenticationService {

    private final CustomerService customerService;
    private final UserRepository userRepository;
    private final AuthenticationProvider authenticationProvider;
    private final JWTService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CustomerRepository customerRepository;

    public AuthenticationService(CustomerService customerService,UserRepository userRepository,
    		AuthenticationProvider authenticationProvider,JWTService jwtService,RefreshTokenRepository refreshTokenRepository,
    		CustomerRepository customerRepository) {
        this.customerService = customerService;
       this.userRepository=userRepository;
       this.authenticationProvider=authenticationProvider;
       this.jwtService=jwtService;
       this.refreshTokenRepository=refreshTokenRepository;
       this.customerRepository=customerRepository;
       
    }

    private RefreshToken createRefreshToken(User user) {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setCreateTime(new Date());
		refreshToken.setExpiredDate(new Date(System.currentTimeMillis() + 1000*60*60*4));
		refreshToken.setRefreshToken(UUID.randomUUID().toString());
		refreshToken.setUser(user);
		return refreshToken;
	}
    
  
    public AuthResponse register(AuthRequest request) {
      
      DtoCustomer dtoCustomer =customerService.saveCustomer(request);
      
      try {
    	  Optional<User> optUser=userRepository.findByCustomerId(dtoCustomer.getId());
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(optUser.get().getUsername(), request.getPassword());
			authenticationProvider.authenticate(authenticationToken);
			
			String accessToken = jwtService.generateToken(optUser.get());
			RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(optUser.get()));
		
			return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken(),optUser.get().getCustomer().getId());
		} catch (Exception e) {
			throw new BaseException(new ErrorMessage(MessageType.USERNAME_OR_PASSWORD_INVALID, e.getMessage()));
		}
   
      
    
    }
    public String extractUsernameFromEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Geçersiz email adresi: " + email);
        }
        return email.substring(0, email.indexOf("@"));
    }
    public AuthResponse login(AuthRequest request) {
    	String userName=extractUsernameFromEmail(request.getEmail());
         try {
        	 Optional<User> optUser=userRepository.findByUsername(userName);
        	 
        	 if (optUser.isEmpty()) {
                 throw new BaseException(new ErrorMessage(MessageType.USERNAME_OR_PASSWORD_INVALID, "User not found"));
             }

             User user = optUser.get();
        	 
   			UsernamePasswordAuthenticationToken authenticationToken = 
   					new UsernamePasswordAuthenticationToken(optUser.get().getUsername(), request.getPassword());
   			authenticationProvider.authenticate(authenticationToken);
   			
   			
   			Date now = new Date();
   	        userRepository.updateLastLoginTime(user.getId(), now);
   	        customerRepository.updateLastLoginTime(user.getCustomer().getId(), now);
   			
   		 
   	        Optional<RefreshToken> optRefreshToken = refreshTokenRepository.findById(user.getId());
   	        if (optRefreshToken.isPresent()) {
   	            RefreshToken refreshToken = optRefreshToken.get();
   	            if (refreshToken.getExpiredDate().after(new Date())) {
   	                
   	                String newAccessToken = jwtService.generateToken(user);
   	                return new AuthResponse(newAccessToken, refreshToken.getRefreshToken(),optUser.get().getCustomer().getId());
   	            } else {
   	                
   	                refreshTokenRepository.delete(refreshToken);
   	                
   	                String newAccessToken = jwtService.generateToken(user);
   	                RefreshToken newRefreshToken = refreshTokenRepository.save(createRefreshToken(user));
   	                return new AuthResponse(newAccessToken, newRefreshToken.getRefreshToken(),optUser.get().getCustomer().getId());
   	            }
   	        }
   			
   		
   			
   			String accessToken = jwtService.generateToken(optUser.get());
   			RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(optUser.get()));
   			
   			return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken(),optUser.get().getCustomer().getId());
   		} catch (Exception e) {
   			throw new BaseException(new ErrorMessage(MessageType.USERNAME_OR_PASSWORD_INVALID, e.getMessage()));
   		}
    }
    
    public AuthResponse loginAdmin(AuthRequest request) {
    	String userName=extractUsernameFromEmail(request.getEmail());
         try {
        	 Optional<User> optUser=userRepository.findByUsername(userName);
        	 
        	 if (optUser.isEmpty()) {
                 throw new BaseException(new ErrorMessage(MessageType.USERNAME_OR_PASSWORD_INVALID, "User not found"));
             }
        	
             User user = optUser.get();
             if (user.getRole() != Role.ADMIN) {
                 throw new BaseException(new ErrorMessage(MessageType.USER_NOT_HAVE_PERMISSIONS, "User dont have access permissions"));
             }
   			UsernamePasswordAuthenticationToken authenticationToken = 
   					new UsernamePasswordAuthenticationToken(optUser.get().getUsername(), request.getPassword());
   			authenticationProvider.authenticate(authenticationToken);
   			
   			
   			Date now = new Date();
   	        userRepository.updateLastLoginTime(user.getId(), now);
   	        customerRepository.updateLastLoginTime(user.getCustomer().getId(), now);
   			
   		
   	        Optional<RefreshToken> optRefreshToken = refreshTokenRepository.findById(user.getId());
   	        if (optRefreshToken.isPresent()) {
   	            RefreshToken refreshToken = optRefreshToken.get();
   	            if (refreshToken.getExpiredDate().after(new Date())) {
   	                
   	                String newAccessToken = jwtService.generateToken(user);
   	                return new AuthResponse(newAccessToken, refreshToken.getRefreshToken(),optUser.get().getCustomer().getId());
   	            } else {
   	               
   	                refreshTokenRepository.delete(refreshToken);
   	               
   	                String newAccessToken = jwtService.generateToken(user);
   	                RefreshToken newRefreshToken = refreshTokenRepository.save(createRefreshToken(user));
   	                return new AuthResponse(newAccessToken, newRefreshToken.getRefreshToken(),optUser.get().getCustomer().getId());
   	            }
   	        }
   			
   		
   			
   			String accessToken = jwtService.generateToken(optUser.get());
   			RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(optUser.get()));
   			
   			return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken(),optUser.get().getCustomer().getId());
   		} catch (Exception e) {
   			throw new BaseException(new ErrorMessage(MessageType.USERNAME_OR_PASSWORD_INVALID, e.getMessage()));
   		}
    }
    
 
    }

	
