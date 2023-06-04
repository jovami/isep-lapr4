package jovami.util.grammar;

import java.io.File;
import java.io.IOException;

/**
 * GrammarValidator
 *
 * Generalization of a grammar validation service
 * that can be used an adapter when a system has
 * a compiler-compiler project---such as ANLTR---as a dependency
 * but wants flexibility to later swap to a different project
 * while keeping code changes to a bare minimum
 */
public interface GrammarValidator {

    /**
     * @param specFile File path to the formative exam specification
     * @return whether the provided file obeys the specification grammar
     * @throws IOException
     */
    boolean validate(File specFile) throws IOException;

    /**
     * @param spec string that contains the entire formative exam specification
     * @return whether the provided file obeys the specification grammar
     */
    boolean validate(String spec);
}
