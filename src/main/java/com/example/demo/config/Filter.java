package com.example.demo.config;

import com.example.demo.entity.User;
import com.example.demo.exception.AuthorizeException;
import com.example.demo.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.security.SignatureException;
import java.util.List;


@Component
public class Filter extends OncePerRequestFilter {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    HandlerExceptionResolver resolver;

    @Autowired
    TokenService tokenService;

    List<String> PUBLIC_API = List.of(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/api/test",
            "/api/login",
            "/api/register",
            "/api/expertregister",
            "/api/update/{id}",
            "/api/getExpertById/{id}",
            "/api/getAllExpert",
            "/api/AdminAccount",
            "/api/deleteById/{id}",
            "/api/resetPassword",
            "/api/resetPassword/**"
    );

    //nhận bt api nào là api cần phân quyền
    boolean isPermitted(HttpServletRequest request) {
        AntPathMatcher patchMatch = new AntPathMatcher();
        String uri = request.getRequestURI();
        String method = request.getMethod(); //post, put, delete

        if(method.equals("GET") && patchMatch.match("/api/product/**", uri)) {
            return true; //public api (còn api khác thì check như cũ)
        }

        return PUBLIC_API.stream().anyMatch(item -> patchMatch.match(item, uri));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI();
        if (isPermitted(request)) {
            filterChain.doFilter(request, response);
        } else {
            String token = getToken(request);

            if (token == null) {
                resolver.resolveException(request, response, null, new AuthorizeException("Authentication token is missing!"));
            }

            User user = null;

            try {
                user = tokenService.getAccountByToken(token);
            } catch (MalformedJwtException malformedJwtException) {
                resolver.resolveException(request, response, null, new AuthorizeException("Authentication token is invalid!"));
            } catch (ExpiredJwtException expiredJwtException) {
                resolver.resolveException(request, response, null, new AuthorizeException("Authentication token is expired!"));
            } catch (Exception exception) {
                resolver.resolveException(request, response, null, new AuthorizeException("Authentication token is invalid!"));
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, token, user.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request, response);
        }
    }

    String getToken(HttpServletRequest request) {
//        return request.getHeader("Authorization").substring(7);
        String token = request.getHeader("Authorization");
        if(token == null) return null;
        return token.substring(7);
    }
}




