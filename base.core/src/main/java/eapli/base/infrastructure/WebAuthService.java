package eapli.base.infrastructure;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.Role;

/**
 * WebAuth
 */
@Service
public final class WebAuthService {
    private final AuthorizationService authz;

    public WebAuthService() {
        this.authz = AuthzRegistry.authorizationService();
    }

    public void ensureLoggedInWithRoles(Role... roles) {
        if (!this.authz.isAuthenticatedUserAuthorizedTo(roles))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
}
