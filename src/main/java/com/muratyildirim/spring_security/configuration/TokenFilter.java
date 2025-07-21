package com.muratyildirim.spring_security.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.muratyildirim.spring_security.auth.token.TokenService;
import com.muratyildirim.spring_security.user.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenFilter extends OncePerRequestFilter {

	TokenService tokenService;

	@SuppressWarnings("unused")
	private HandlerExceptionResolver exceptionResolver;

	public TokenFilter(TokenService tokenService,@Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
		this.tokenService = tokenService;
		this.exceptionResolver = exceptionResolver;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String tokenWithPrefix = getTokenWithPrefix(request);
		if (tokenWithPrefix != null) {
			User user = tokenService.verifyToken(tokenWithPrefix);
			if (user != null) {
				CurrentUser currentUser = new CurrentUser(user);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						currentUser, null, currentUser.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		filterChain.doFilter(request, response);
	}

	private String getTokenWithPrefix(HttpServletRequest request) {
		var tokenWithPrefix = request.getHeader("Authorization");
		var cookies = request.getCookies();
		if (cookies == null)
			return tokenWithPrefix;
		for (var cookie : cookies) {
			if (!cookie.getName().equals("murtimur-access-token"))
				continue;
			if (cookie.getValue() == null || cookie.getValue().isEmpty())
				continue;
			return "AnyPrefix " + cookie.getValue();
		}
		return tokenWithPrefix;
	}

}
