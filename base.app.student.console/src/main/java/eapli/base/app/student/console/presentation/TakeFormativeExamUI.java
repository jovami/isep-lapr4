package eapli.base.app.student.console.presentation;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Component;

import eapli.framework.actions.Action;
import jovami.util.net.NetTools;

/**
 * TakeFormativeExamUI
 */
@Component
public class TakeFormativeExamUI implements Action {

    @Autowired
    private ServerProperties props;

    @Override
    public boolean execute() {
        final var url = String.format("http://localhost:%d/#/formative", this.props.getPort());

        try {
            NetTools.browseURL(new URI(url));
            System.out.printf("Formative exams are to be answered in a web page,\n"
                    + "which should've already opened in your default browser\n"
                    + "\nIf not, open %s in your prefered web browser\n", url);
        } catch (IOException | URISyntaxException e) {
            System.out.printf("Failed to open the URL\n"
                    + "You can still take your formative exams by opening %s "
                    + "in your prefered browser\n", url);
        }

        return false;
    }

}
