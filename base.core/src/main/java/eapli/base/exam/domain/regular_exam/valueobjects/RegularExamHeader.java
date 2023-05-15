package eapli.base.exam.domain.regular_exam.valueobjects;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Invariants;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;
import java.util.Optional;

@Embeddable
public class RegularExamHeader implements ValueObject {

    private Optional<String> header;

    private RegularExamHeaderDescription description;

    public RegularExamHeader(String header, RegularExamHeaderDescription description)
    {
        this.header = Optional.ofNullable(header);
        this.description = description;
    }

    public RegularExamHeader(String header) {
        this.header = Optional.ofNullable(header);
    }

    //for ORM
    protected RegularExamHeader()
    {
        this.header = null;
        this.description = null;

    }
    
    protected RegularExamHeader valueOf(final String header, RegularExamHeaderDescription description)
    {
        return new RegularExamHeader(header,description);
    }

    protected RegularExamHeader valueOf(final String header)
    {
        return new RegularExamHeader(header);
    }
    protected RegularExamHeaderDescription description(){return this.description;}
}
