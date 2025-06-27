package com.qentelli.employeetrackingsystem.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.qentelli.employeetrackingsystem.entity.User;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return Optional.of("System");
        }

        Object principal = auth.getPrincipal();
        if (principal instanceof User user) {
            return Optional.of(user.getFirstName() + " " + user.getLastName());
        }

        return Optional.of(auth.getName()); // fallback (username or email)
    }
}