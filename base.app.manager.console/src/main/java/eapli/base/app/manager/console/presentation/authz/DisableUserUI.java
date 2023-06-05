/*
 * Copyright (c) 2013-2022 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eapli.base.app.manager.console.presentation.authz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eapli.base.clientusermanagement.dto.SystemUserNameEmailDTO;
import eapli.base.clientusermanagement.usermanagement.application.DisableUserController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

/**
 *
 * @author Fernando
 */
@SuppressWarnings("squid:S106")
public class DisableUserUI extends AbstractUI {
    private static final Logger LOGGER = LoggerFactory.getLogger(DisableUserUI.class);

    private final DisableUserController theController = new DisableUserController();

    @Override
    protected boolean doShow() {
        boolean keepDisabling = true;
        do {
            final Iterable<SystemUserNameEmailDTO> iterable = this.theController.enabledUsers();
            if (!iterable.iterator().hasNext()) {
                System.out.println("There is no registered User");
                keepDisabling = false;
            } else {
                SelectWidget<SystemUserNameEmailDTO> selec = new SelectWidget<>("Choose a user to disable", iterable);
                selec.show();
                keepDisabling = selec.selectedElement() != null;
                if (keepDisabling) {
                    try {
                        if (this.theController.disableUser(selec.selectedElement())) {
                            System.out.printf("%s was successfully disabled\n", selec.selectedElement().username());
                        } else {
                            System.out.printf("There was an error disabling %s\n", selec.selectedElement().username());
                        }
                    } catch (IntegrityViolationException | ConcurrencyException ex) {
                        LOGGER.error("Error performing the operation", ex);
                        System.out.println(
                                "Unfortunatelly there was an unexpected error in the application. Please try again and if the problem persists, contact your system admnistrator.");
                    }
                }
            }
        } while (keepDisabling);
        return true;
    }

    @Override
    public String headline() {
        return "Deactivate User";
    }
}
