package ru.calorai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.calorai.model.AuthProvider;
import ru.calorai.model.UserDetailsImpl;
import ru.calorai.model.UserEntity;
import ru.calorai.repository.UserRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository users;

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        UserEntity u = users.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("Not found: " + email));
//
//        if (Objects.equals(u.getAuthProvider(), AuthProvider.GOOGLE.name()) && u.getPasswordHash() == null) {
//            throw new UsernameNotFoundException("Use Google sign-in for this account");
//        }
//
//        return org.springframework.security.core.userdetails.User
//                .withUsername(u.getEmail())
//                .password(u.getPasswordHash())
//                .authorities(
//                        u.getRoles().stream()
//                                .map(r -> "ROLE_" + r.getName())
//                                .toArray(String[]::new)
//                )                .disabled(!u.getEnabled())
//                .build();
//    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = users.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return UserDetailsImpl.fromEntity(user);
    }

    public UserDetails loadUserById(Long id) {
        var user = users.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
        return UserDetailsImpl.fromEntity(user);
    }
}
