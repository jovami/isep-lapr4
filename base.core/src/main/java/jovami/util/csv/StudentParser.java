package jovami.util.csv;


import eapli.base.clientusermanagement.usermanagement.application.AddUserController;
import eapli.framework.infrastructure.authz.domain.model.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentParser implements CSVParser{

    private final AddUserController addUserController = new AddUserController();


    private enum StudentColumns {
        USERNAME(0),
        PASSWORD(1),
        FIRSTNAME(2),
        LASTNAME(3),
        FULLNAME(4),
        EMAIL(5),
        DATE_OF_BIRTH(6),
        TAX_PAYER_NUMBER(7),
        MECANOGRAPHICNUMBER(8);

        private final int col;
        StudentColumns(int col) {
            this.col = col;
        }
    }

    /**
     * @param data the data
     */

    @Override
    public void parse(List<String[]> data) {

        final Set<Role> roleTypes = new HashSet<>();
        Role s = Role.valueOf("student");
        roleTypes.add(s);

        data.forEach(line -> {
            String username, password, firstName, lastName, fullName, email,
                    dateOfBirth, taxPayerNumber, mecanographicNumber;

            username = line[StudentColumns.USERNAME.col];
            password = line[StudentColumns.PASSWORD.col];
            firstName = line[StudentColumns.FIRSTNAME.col];
            lastName = line[StudentColumns.LASTNAME.col];
            fullName = line[StudentColumns.FULLNAME.col];
            email = line[StudentColumns.EMAIL.col];
            dateOfBirth = line[StudentColumns.DATE_OF_BIRTH.col];
            taxPayerNumber = line[StudentColumns.TAX_PAYER_NUMBER.col];
            mecanographicNumber = line[StudentColumns.MECANOGRAPHICNUMBER.col];

            String shortName = firstName + " " + lastName;


            this.addUserController.addUser(username, password, firstName, lastName, email, roleTypes);
            this.addUserController.addStudent(mecanographicNumber,fullName,shortName,dateOfBirth,taxPayerNumber);
        });

    }
}
