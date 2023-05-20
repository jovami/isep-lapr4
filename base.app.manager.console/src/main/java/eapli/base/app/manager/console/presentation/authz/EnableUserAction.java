package eapli.base.app.manager.console.presentation.authz;

import eapli.framework.actions.Action;

public class EnableUserAction implements Action {
    @Override
    public boolean execute() {
        return new EnableUserUI().show();
    }
}
