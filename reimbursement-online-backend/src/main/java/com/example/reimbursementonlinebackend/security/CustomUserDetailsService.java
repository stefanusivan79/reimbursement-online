package com.example.reimbursementonlinebackend.security;

import com.example.reimbursementonlinebackend.domain.User;
import com.example.reimbursementonlinebackend.repository.UserRepository;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service(value = "userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String input) {
        User user;

        if (input.contains("@"))
            user = userRepository.findOneByEmailIgnoreCase(input)
                    .orElseThrow(() -> new BadCredentialsException("Bad credentials"));
        else
            user = userRepository.findOneByUsername(input)
                    .orElseThrow(() -> new BadCredentialsException("Bad credentials"));

        new AccountStatusUserDetailsChecker().check(user);

        return user;
    }

}
