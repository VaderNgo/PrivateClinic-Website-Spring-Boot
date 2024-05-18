package org.example.privateclinicwebsitespringboot.Handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;

public class AuthenticationHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(
                grantedAuthority -> grantedAuthority
                        .getAuthority().equals("ADMIN")
        );
        boolean isDoctor = authentication.getAuthorities().stream().anyMatch(
                grantedAuthority -> grantedAuthority
                        .getAuthority().equals("DOCTOR")
        );
        if(isAdmin){
            setDefaultTargetUrl("/admin/dashboard");
        }else if (isDoctor){
            setDefaultTargetUrl("/doctor/dashboard");
        }
        else{
            setDefaultTargetUrl("/user/dashboard");
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
