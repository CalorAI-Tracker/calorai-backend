package ru.calorai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ru.calorai.model.AuthProvider;
import ru.calorai.model.RoleEntity;
import ru.calorai.model.UserEntity;
import ru.calorai.repository.RoleRepository;
import ru.calorai.repository.UserRepository;
import ru.calorai.users.ERole;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        try {
            return processOAuth2User(oauth2User);
        } catch (Exception ex) {
            throw new OAuth2AuthenticationException("OAuth2 login failed");
        }
    }

    private OAuth2User processOAuth2User(OAuth2User oauth2User) {
        String sub = oauth2User.getAttribute("sub");
        String email = oauth2User.getAttribute("email");

        if (email == null || email.isBlank()) {
            throw new OAuth2AuthenticationException("Email not provided by OAuth2 provider");
        }

        UserEntity user = userRepository.findByEmail(email)
                .map(existingUser -> updateExistingUser(existingUser, sub))
                .orElseGet(() -> createNewUser(email, sub));

        return createSpringSecurityUser(user, oauth2User);
    }

    private UserEntity updateExistingUser(UserEntity u, String sub) {
        if (u.getProviderId() == null) {
            u.setProviderId(sub);
            u.setAuthProvider(AuthProvider.GOOGLE.name());
        }
        u.setLastLoginAt(OffsetDateTime.now());
        return userRepository.save(u);
    }

    private UserEntity createNewUser(String email, String providerSub) {
        UserEntity newUser = UserEntity.builder()
                .email(email)
                .providerId(providerSub)
                .authProvider(AuthProvider.GOOGLE.name())
                .emailVerified(true)
                .enabled(true)
                .createdAt(OffsetDateTime.now())
                .lastLoginAt(OffsetDateTime.now())
                .roles(new HashSet<>())
                .build();

        // Добавляем дефолтную роль USER
        roleRepository.findByName(ERole.USER.name())
                .ifPresent(role -> newUser.getRoles().add(role));

        return userRepository.save(newUser);
    }

    private OAuth2User createSpringSecurityUser(UserEntity user, OAuth2User oauth2User) {
        Map<String, Object> attributes = new HashMap<>(oauth2User.getAttributes());
        attributes.put("userId", user.getId());
        attributes.put("email", user.getEmail());

        return new DefaultOAuth2User(
                getAuthorities(user.getRoles()),
                attributes,
                "email"
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<RoleEntity> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());
    }
}
