package eapli.base.exam.domain.regular_exam.valueobjects;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import org.apache.commons.io.FileUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Embeddable
public class RegularExamSpecification implements ValueObject {

    @Column(columnDefinition = "CLOB")
    private final String specification;

    protected RegularExamSpecification() {
        this.specification = null;
    }

    public RegularExamSpecification(String specification)
    {
        Preconditions.noneNull(specification, "Regular Exam specification cannot be null");
        Preconditions.nonEmpty(specification, "Regular Exam specification cannot be empty");
        this.specification = specification;
    }

    public static RegularExamSpecification valueOf(final File fp) throws IOException {
        return new RegularExamSpecification(FileUtils.readFileToString(fp, StandardCharsets.UTF_8));
    }

    public static RegularExamSpecification valueOf(Iterable<String> lines) {
        return new RegularExamSpecification(
                StreamSupport.stream(lines.spliterator(), false).collect(Collectors.joining("\n")));
    }

    public String specificationString() { return this.specification; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (obj == null || this.getClass() != obj.getClass())
            return false;

        var o = (RegularExamSpecification) obj;
        return this.specification.equals(o.specification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.specification);
    }


    @Override
    public String toString() {
        return String.format(this.specification);
    }


}

