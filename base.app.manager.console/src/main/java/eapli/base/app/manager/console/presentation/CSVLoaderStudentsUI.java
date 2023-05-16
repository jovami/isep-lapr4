package eapli.base.app.manager.console.presentation;

import eapli.framework.presentation.console.AbstractUI;
import jovami.util.csv.aplication.CSVLoaderStudentsController;
import jovami.util.csv.InputReader;



public class CSVLoaderStudentsUI extends AbstractUI {

    private final CSVLoaderStudentsController ctrl;

    public CSVLoaderStudentsUI()
    {
        super();
        this.ctrl = new CSVLoaderStudentsController();
    }

    @Override
    public boolean doShow()
    {
        try {
            if (InputReader.confirm("Load default file?[YES/NO]", true))
                this.ctrl.loadResources();
        } catch (RuntimeException e) {
            System.err.println("Aborting...");
            throw e;
        }

        System.out.println("Data loaded with success!!");

        return false;
    }

    @Override
    public String headline() {
        return "Import csv student file";
    }



}
