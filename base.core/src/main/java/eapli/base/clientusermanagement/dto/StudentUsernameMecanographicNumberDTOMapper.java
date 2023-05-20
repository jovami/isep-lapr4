package eapli.base.clientusermanagement.dto;

import eapli.base.clientusermanagement.domain.users.Student;
import jovami.util.dto.Mapper;

public final class StudentUsernameMecanographicNumberDTOMapper
        implements Mapper<Student, StudentUsernameMecanographicNumberDTO> {
    @Override
    public StudentUsernameMecanographicNumberDTO toDTO(Student student) {
        return new StudentUsernameMecanographicNumberDTO(student.user().username(), student.mecanographicNumber());
    }
}
