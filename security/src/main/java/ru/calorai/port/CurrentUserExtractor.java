package ru.calorai.port;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.calorai.security.port.in.CurrentUserDetails;
import ru.calorai.security.port.in.CurrentUserExtractorApi;

@Component
public class CurrentUserExtractor implements CurrentUserExtractorApi {

    @Override
    public CurrentUserDetails getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof CurrentUserDetails details) {
            return details;
        }

        return null;
    }
}
