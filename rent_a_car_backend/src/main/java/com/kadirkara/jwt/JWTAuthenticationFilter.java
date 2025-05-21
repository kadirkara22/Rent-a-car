package com.kadirkara.jwt;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kadirkara.exception.BaseException;
import com.kadirkara.exception.ErrorMessage;
import com.kadirkara.exception.MessageType;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String header = request.getHeader("Authorization");
		if(header==null) {
			filterChain.doFilter(request, response);
			return;
		}
		String token;
		String username;
		
		token = header.substring(7);
		
		
		try {
			username = jwtService.getUsernameByToken(token);
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				if(userDetails!=null && jwtService.isTokenValid(token)) {
					List<String> roles = jwtService.getRolesFromToken(token);
					Collection<? extends GrantedAuthority> authorities = roles.stream()
				            .map(SimpleGrantedAuthority::new)
				            .collect(Collectors.toList());
					UsernamePasswordAuthenticationToken authenticationToken = new 
					UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
					
					authenticationToken.setDetails(userDetails);
					
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			}
		}
		catch(ExpiredJwtException ex) {
			throw new BaseException(new ErrorMessage(MessageType.TOKEN_IS_EXPIRED, ex.getMessage()));
		}
		catch (Exception e) {
			throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, e.getMessage()));
		}
		filterChain.doFilter(request, response);
	}

}
