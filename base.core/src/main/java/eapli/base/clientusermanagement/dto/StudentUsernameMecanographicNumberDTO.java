package eapli.base.clientusermanagement.dto;

import java.util.Objects;

import eapli.base.clientusermanagement.domain.users.MecanographicNumber;
import eapli.framework.infrastructure.authz.domain.model.Username;

public class StudentUsernameMecanographicNumberDTO {
    private Username username;
    private MecanographicNumber mecanographicNumber;

    public StudentUsernameMecanographicNumberDTO(Username username, MecanographicNumber mecanographicNumber) {
        this.username = username;
        this.mecanographicNumber = mecanographicNumber;
    }

    @Override
    public String toString() {
        return "Username: " + username.toString() +
                " with Mecanographic Number: " + mecanographicNumber.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        StudentUsernameMecanographicNumberDTO that = (StudentUsernameMecanographicNumberDTO) o;
        return Objects.equals(username, that.username) && Objects.equals(mecanographicNumber, that.mecanographicNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, mecanographicNumber);
    }

    public Username username() {
        return this.username;
    }

    public MecanographicNumber mecanographicNumber() {
        return this.mecanographicNumber;
    }

}
