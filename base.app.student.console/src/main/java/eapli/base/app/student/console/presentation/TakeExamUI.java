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
 * TakeExamUI
 */
@Component
public class TakeExamUI implements Action {

    @Autowired
    private ServerProperties props;

    @Override
    public boolean execute() {
        final var url = String.format("http://localhost:%d/#/regular", this.props.getPort());

        try {
            NetTools.browseURL(new URI(url));
            System.out.printf("Exams are to be answered in a web page,\n"
                    + "which should've already opened in your default browser\n"
                    + "\nIf not, open %s in your preferred web browser\n", url);
        } catch (IOException | URISyntaxException e) {
            System.out.printf("Failed to open the URL\n"
                    + "You can still take your exams by opening %s "
                    + "in your preferred browser\n", url);
        }

        return false;
    }

}
