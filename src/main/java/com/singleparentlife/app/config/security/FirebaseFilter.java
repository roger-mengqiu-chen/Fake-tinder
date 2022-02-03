package com.singleparentlife.app.config.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.singleparentlife.app.constants.HeaderName;
import com.singleparentlife.app.model.User;
import com.singleparentlife.app.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FirebaseFilter extends BasicAuthenticationFilter {

    private final UserDetailsServiceImp userService;

    public FirebaseFilter(AuthenticationManager authenticationManager, UserDetailsServiceImp userService) {
        super(authenticationManager);
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String xAuth = request.getHeader(HeaderName.FIRE_AUTH);

        if (xAuth == null) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(xAuth);
            String uid = decodedToken.getUid();
            System.out.println(uid);
            UserDetails user = userService.loadUserByUsername(uid);
            if (user != null) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
    }
}
