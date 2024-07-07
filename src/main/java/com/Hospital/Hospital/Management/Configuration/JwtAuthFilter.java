package com.Hospital.Hospital.Management.Configuration;

import java.io.IOException;

import lombok.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Hospital.Hospital.Management.Service.Authenticate.JwtService;
import com.Hospital.Hospital.Management.Service.Authenticate.TokenBlacklistService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final UserDetailsService userDetailsService;

    @Autowired
    private final TokenBlacklistService tokenBlacklistService;

    // step 2.
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        // verify whether request has authorization header and it has bearer in it.
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String email;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        // Extract the jwt from Authorization.
        jwt = authHeader.substring(7);

        if (tokenBlacklistService.isTokenBlacklisted(jwt)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // verify whether user is present in DB.
        // verify whether token is valid.
        try {
            email = jwtService.extractUsername(jwt);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return; // Return immediately if JWT validation fails
        }
        // if user is present and no authorization object in securityContext.
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // if valid set to security context holder.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
            // if valid set to security context holder.
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

        }
        filterChain.doFilter(request, response);
    }

    // step 1.
    // verify if it is whitelisted path and if YES then do anything.
    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        return request.getServletPath().contains("/api/v1/auth");
    }

}
