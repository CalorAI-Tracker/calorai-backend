package ru.calorai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import ru.calorai.model.AuthProvider;
import ru.calorai.user.jpa.entity.RoleEntity;
import ru.calorai.user.jpa.entity.UserEntity;
import ru.calorai.user.jpa.repository.RoleRepository;
import ru.calorai.user.jpa.repository.UserRepository;
import ru.calorai.heathProfile.enums.ERole;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OidcUserServiceImpl extends OidcUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        // загрузим OIDC-пользователя (с проверенным id_token)
        OidcUser oidcUser = super.loadUser(userRequest);

        try {
            return processOidcUser(oidcUser);
        } catch (Exception ex) {
            throw new OAuth2AuthenticationException("OIDC login failed");
        }
    }

    private OidcUser processOidcUser(OidcUser oidcUser) {
        String sub = oidcUser.getSubject();     // стабильный ID у провайдера
        String email = oidcUser.getEmail();     // email из id_token/userinfo

        if (email == null || email.isBlank()) {
            throw new OAuth2AuthenticationException("Email not provided by OIDC provider");
        }

        UserEntity user = userRepository.findByEmail(email)
                .map(u -> updateExistingUser(u, sub))
                .orElseGet(() -> createNewUser(email, sub));

        // authorities из ролей пользователя
        Collection<? extends GrantedAuthority> authorities = mapAuthorities(user.getRoles());

        // возвращаем OidcUser (атрибуты – это claims id_token + userinfo)
        // nameAttributeKey пусть будет "email" — тогда getName() вернёт email
        return new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo(), "email");
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

        roleRepository.findByName(ERole.USER.name())
                .ifPresent(role -> newUser.getRoles().add(role));

        return userRepository.save(newUser);
    }

    private Collection<? extends GrantedAuthority> mapAuthorities(Set<RoleEntity> roles) {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName()))
                .collect(Collectors.toList());
    }
}
