package org.ms.requestmanager.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ms.requestmanager.entities.AppRole;
import org.ms.requestmanager.entities.AppUser;
import org.ms.requestmanager.exceptions.RessourceNotFoundException;
import org.ms.requestmanager.repositories.AppUserRepository;
import org.ms.requestmanager.services.PersonalService;
import org.ms.requestmanager.services.StudentService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final AppUserRepository appUserRepository;
    private final StudentService studentService;
    private final PersonalService personalService;
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, AppUserRepository appUserRepository, StudentService studentService, PersonalService personalService) {
        this.authenticationManager = authenticationManager;
        this.appUserRepository = appUserRepository;
        this.studentService = studentService;
        this.personalService = personalService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        AppUser appUser = appUserRepository.findByUsername(username);
        if(appUser == null) {
            throw new RessourceNotFoundException("Unknown User");
        }
        else{
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username,password);
            return authenticationManager.authenticate(authenticationToken);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        User user = (User) authResult.getPrincipal();
        Algorithm algo1 = Algorithm.HMAC256(SecurityParams.SECRET);
        String jwtAccessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+ SecurityParams.EXPIRE_ACCESS_TOKEN))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algo1);
        String jwtRefreshAccessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+ SecurityParams.EXPIRE_ACCESS_REFRESH_TOKEN))
                .withIssuer(request.getRequestURL().toString())
                .sign(algo1);
        Map<Object,Object> idToken = new HashMap<>();
        AppUser appUser = appUserRepository.findByUsername(request.getParameter("username"));
        String role = ((List<AppRole>) appUser.getAppRoles()).get(0).getRolename();
        idToken.put("userId",appUser.getId());
        idToken.put("rolesUser", role);
        idToken.put("access_token",jwtAccessToken);
        idToken.put("refresh_token",jwtRefreshAccessToken);
        Object o = null;
        if (role.equals("STUDENT")) {
            o = studentService.getStudentByUser(appUser.getId());
        } else if (role.equals("PERSONAL")) {
            o = personalService.getPersonalByUser(appUser.getId());
        }
        if(o != null) idToken.put("user", o.getClass().getName());
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(),idToken);
    }
}
