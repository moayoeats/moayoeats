package com.moayo.moayoeats.global.security;

import com.moayo.moayoeats.global.exception.*;
import lombok.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new GlobalException(NOT_EXIST_USER));

        return new UserDetailsImpl(user);
    }
}