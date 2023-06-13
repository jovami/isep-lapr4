package eapli.base.app.student.console.application;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * WebUIController
 */
@Controller
public class WebUIController {

    @GetMapping("")
    public String index(Model model) {
        return "index"; // load index.html
    }
}
