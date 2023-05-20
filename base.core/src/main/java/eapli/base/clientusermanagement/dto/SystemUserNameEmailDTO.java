package eapli.base.clientusermanagement.dto;

import java.util.Objects;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Username;

public class SystemUserNameEmailDTO {
    private Username username;
    private EmailAddress email;

    public SystemUserNameEmailDTO(Username username, EmailAddress email) {
        this.username = username;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Username= " + username.toString() +
                ", email=" + email.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SystemUserNameEmailDTO that = (SystemUserNameEmailDTO) o;
        return Objects.equals(username, that.username) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email);
    }

    public Username username() {
        return this.username;
    }

    public EmailAddress email() {
        return this.email;
    }

}
