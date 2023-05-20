package eapli.base.app.student.console.presentation;

import java.time.LocalDateTime;

import eapli.base.exam.application.ListFutureExamsController;
import eapli.base.exam.dto.FutureExamDTO;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.visitor.Visitor;

/**
 * ListFutureExamsUI
 */
public final class ListFutureExamsUI extends AbstractListUI<FutureExamDTO> {
    private final ListFutureExamsController ctrl;

    public ListFutureExamsUI() {
        super();

        this.ctrl = new ListFutureExamsController();
    }

    @Override
    protected String elementName() {
        return "Exam";
    }

    @Override
    protected Visitor<FutureExamDTO> elementPrinter() {
        return System.out::println;
    }

    @Override
    protected Iterable<FutureExamDTO> elements() {
        return this.ctrl.futureExams(LocalDateTime.now());
    }

    @Override
    protected String emptyMessage() {
        return "No future exams scheduled!";
    }

    @Override
    protected String listHeader() {
        return "Future Exams:";
    }

    @Override
    public String headline() {
        return "List Future Exams";
    }
}
