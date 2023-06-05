package eapli.base.app.manager.console.presentation.authz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eapli.base.clientusermanagement.dto.SystemUserNameEmailDTO;
import eapli.base.clientusermanagement.usermanagement.application.EnableUserController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

public class EnableUserUI extends AbstractUI {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(eapli.base.app.manager.console.presentation.authz.DisableUserUI.class);

    private final EnableUserController theController = new EnableUserController();

    @Override
    protected boolean doShow() {
        boolean keepEnabling = true;
        do {
            final Iterable<SystemUserNameEmailDTO> iterable = this.theController.disabledUsers();
            if (!iterable.iterator().hasNext()) {
                System.out.println("There is no users disabled");
                keepEnabling = false;
            } else {
                SelectWidget<SystemUserNameEmailDTO> selec = new SelectWidget<>("Choose a user no enable", iterable);
                selec.show();
                keepEnabling = selec.selectedElement() != null;
                if (keepEnabling) {
                    try {
                        if (this.theController.enableUser(selec.selectedElement())) {
                            System.out.printf("%s was successfully enabled\n", selec.selectedElement().username());
                        } else {
                            System.out.printf("There was an error enabling %s\n", selec.selectedElement().username());
                        }

                    } catch (IntegrityViolationException | ConcurrencyException | IllegalAccessException ex) {
                        LOGGER.error("Error performing the operation", ex);
                        System.out.println(
                                "Unfortunatelly there was an unexpected error in the application. Please try again and if the problem persists, contact your system admnistrator.");
                    }
                }
            }
        } while (keepEnabling);
        return true;
    }

    @Override
    public String headline() {
        return "Enable User";
    }
}
