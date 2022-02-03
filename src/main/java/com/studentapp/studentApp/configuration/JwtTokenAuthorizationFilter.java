package com.studentapp.studentApp.configuration;

import com.studentapp.studentApp.model.Student;
import com.studentapp.studentApp.repository.StudentRepository;
import com.studentapp.studentApp.util.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtTokenAuthorizationFilter extends OncePerRequestFilter {


    private final JwtToken jwtToken;
    private final StudentRepository studentRepository;


    public JwtTokenAuthorizationFilter(JwtToken jwtToken, StudentRepository studentRepository) {
        this.jwtToken = jwtToken;
        this.studentRepository = studentRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || authHeader.isEmpty() || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        final String token = authHeader.split(" ")[1].trim();
        if (!jwtToken.isValid(token)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        Optional<Student> student = studentRepository.findStudentByUsername(jwtToken.getUsername(token));


        if (student.isPresent()) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    student.get(), student.get().getUsername(), student.get().getAuthorities()
            );

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
