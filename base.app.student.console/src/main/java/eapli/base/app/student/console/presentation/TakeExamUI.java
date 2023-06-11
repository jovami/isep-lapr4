package eapli.base.app.student.console.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Hello
 * TODO: change name
 */
@Controller
public class TakeExamUI {

    @GetMapping("")
    public String index(Model model) {
        return "index";
    }
}
