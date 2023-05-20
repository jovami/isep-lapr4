package eapli.base.clientusermanagement.dto;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jovami.util.dto.Mapper;

public final class SystemUserNameEmailDTOMapper implements Mapper<SystemUser, SystemUserNameEmailDTO> {
    @Override
    public SystemUserNameEmailDTO toDTO(SystemUser user) {
        return new SystemUserNameEmailDTO(user.username(), user.email());
    }
}
