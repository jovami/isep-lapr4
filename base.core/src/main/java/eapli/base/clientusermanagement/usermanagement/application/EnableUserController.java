package eapli.base.clientusermanagement.usermanagement.application;

import java.util.Comparator;
import java.util.Optional;

import org.apache.commons.lang3.reflect.FieldUtils;

import eapli.base.clientusermanagement.dto.SystemUserNameEmailDTO;
import eapli.base.clientusermanagement.dto.SystemUserNameEmailDTOMapper;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserManagementService;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;

@UseCaseController
public class EnableUserController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final UserManagementService userSvc = AuthzRegistry.userService();

    private final UserRepository repo = PersistenceContext.repositories().users();

    public Iterable<SystemUserNameEmailDTO> disabledUsers() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.MANAGER);

        return new SystemUserNameEmailDTOMapper().toDTO(userSvc.deactivatedUsers(),
                Comparator.comparing(SystemUser::identity));
    }

    public boolean enableUser(SystemUserNameEmailDTO dto) throws IllegalAccessException {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.MANAGER);
        Optional<SystemUser> systemUser = repo.ofIdentity(dto.username());
        if (systemUser.isPresent()) {
            FieldUtils.writeField(systemUser.get(), "active", true, true);
            repo.save(systemUser.get());
            return true;
        } else {
            return false;
        }

    }
}
