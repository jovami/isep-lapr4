package eapli.base.app.manager.console.presentation;

import java.io.File;
import java.io.FileNotFoundException;

import eapli.base.enrollment.aplication.CSVLoaderStudentsController;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;

public class CSVLoaderStudentsUI extends AbstractUI {

    private final CSVLoaderStudentsController ctrl;

    public CSVLoaderStudentsUI() {
        super();
        this.ctrl = new CSVLoaderStudentsController();
    }

    @Override
    public boolean doShow() {
        String filePath = Console.readLine("Type file path:");
        File file = new File(filePath);

        try {
            if (file.exists() && file.canRead())
                this.ctrl.file(file);
            else
                System.out.println("File doesnt exist");
        } catch (FileNotFoundException e) {
            System.out.println("File doesnt exist");
        }

        System.out.println("Data loaded with success!!");
        return false;
    }

    @Override
    public String headline() {
        return "Import csv student file";
    }
}
