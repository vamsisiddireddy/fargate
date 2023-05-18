package com.motivity.hospital.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.motivity.hospital.jwt.service.JWTServiceData;
import com.motivity.hospital.jwt.utilities.JWTUtility;

@Service
public class JWTFilter extends OncePerRequestFilter{
	@Autowired
	private JWTUtility jwtUtility;
	@Autowired
	private JWTServiceData adminServiceData;
	Logger logger = LoggerFactory.getLogger(JWTFilter.class);



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authorization=request.getHeader("Authorization");
		logger.info(authorization);
		String token=null;
		String username=null;
		if(null!=authorization && authorization.startsWith("Bearer ")) {

			token = authorization.substring(7);
			username = jwtUtility.getUsernameFromToken(token);

		}
//		if(null!=username &&SecurityContextHolder.getContext().getAuthentication()==null) {
		if(null!=username &&SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails = adminServiceData.loadUserByUsername(username);
			if (jwtUtility.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
						= new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(
						new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//				logger.info("authentication after " + SecurityContextHolder.getContext().getAuthentication());
			}
		}
		filterChain.doFilter(request, response);
		
	}

}

