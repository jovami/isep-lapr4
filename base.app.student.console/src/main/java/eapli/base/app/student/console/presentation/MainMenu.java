/*
 * Copyright (c) 2013-2022 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.base.app.student.console.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eapli.base.Application;
import eapli.base.app.common.console.AcceptRejectMeetingRequestUI;
import eapli.base.app.common.console.CancelMeetingUI;
import eapli.base.app.common.console.ScheduleMeetingUI;
import eapli.base.app.common.console.presentation.ListMeetingParticipantsUI;
import eapli.base.app.common.console.presentation.authz.CreateBoardUI;
import eapli.base.app.common.console.presentation.authz.CreatePostItUI;
import eapli.base.app.common.console.presentation.authz.ListBoardUI;
import eapli.base.app.common.console.presentation.authz.MyUserMenu;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.framework.actions.Actions;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ExitWithMessageAction;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;

@Component
public class MainMenu extends AbstractUI {

    private static final String RETURN_LABEL = "Return ";

    private static final int EXIT_OPTION = 0;

    // Course
    private static final int ENROLLMENT_REQUEST_OPTION = 1;
    private static final int LIST_COURSES = 2;

    // EXAM
    private static final int LIST_FUTURE_EXAMS = 1;
    private static final int TAKE_EXAM = 2;
    private static final int LIST_GRADES = 3;

    // FORMATIVE EXAM
    private static final int TAKE_FORMATIVE_EXAM = 1;

    // BOARD
    private static final int CREATE_BOARD_OPTION = 1;
    private static final int LIST_BOARD_OPTION = 2;
    private static final int CREATE_POSTIT_OPTION= 3;

    // MEETING
    private static final int SCHEDULE_MEETING = 1;
    private static final int ACCEPT_REJECT_MEETING_REQUEST = 2;
    private static final int LIST_MEETING_PARTICIPANTS = 3;
    private static final int CANCEL_MEETING = 4;

    // SETTINGS
    private static final int MY_USER_OPTION = 1;
    private static final int ENROLLMENTS_OPTION = 2;
    private static final int EXAM_OPTION = 3;
    private static final int FORMATIVE_EXAM_OPTION = 4;
    private static final int BOARD_OPTION = 5;
    private static final int MEETING_OPTION = 6;

    private static final String SEPARATOR_LABEL = "--------------";

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    // NOTE: managed by spring

    @Autowired
    private TakeFormativeExamUI takeFormativeExamUI;

    @Autowired
    private TakeExamUI takeExamUI;

    @Override
    public boolean show() {
        drawFormTitle();
        return doShow();
    }

    /**
     * @return true if the user selected the exit option
     */
    @Override
    public boolean doShow() {
        final Menu menu = buildMainMenu();
        final MenuRenderer renderer;
        renderer = new VerticalMenuRenderer(menu, MenuItemRenderer.DEFAULT);

        return renderer.render();
    }

    @Override
    public String headline() {

        return authz.session().map(s -> "eCourse [ " + s.authenticatedUser().identity() + " ]")
                .orElse("eCourse [ ==Anonymous== ]");
    }

    private Menu buildMainMenu() {
        final Menu mainMenu = new Menu();

        mainMenu.addSubMenu(MY_USER_OPTION, new MyUserMenu());

        if (!Application.settings().isMenuLayoutHorizontal())
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.POWER_USER, BaseRoles.STUDENT)) {
            mainMenu.addSubMenu(ENROLLMENTS_OPTION, buildCourseMenu());
            mainMenu.addSubMenu(EXAM_OPTION, buildExamMenu());
            mainMenu.addSubMenu(FORMATIVE_EXAM_OPTION, buildFormativeExamMenu());
            mainMenu.addSubMenu(BOARD_OPTION, buildBoardMenu());
            mainMenu.addSubMenu(MEETING_OPTION, buildMeetingMenu());
        }

        if (!Application.settings().isMenuLayoutHorizontal())
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));

        mainMenu.addItem(EXIT_OPTION, "Exit", new ExitWithMessageAction("Bye, Bye"));

        return mainMenu;
    }

    private Menu buildCourseMenu() {
        final Menu menu = new Menu("Course");

        menu.addItem(ENROLLMENT_REQUEST_OPTION, "Request Enrollment in a Course", new EnrollmentRequestUI()::show);
        menu.addItem(LIST_COURSES, "List available courses", new ListAvailableCoursesStudentUI()::show);
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildExamMenu() {
        final Menu menu = new Menu("Exams");

        menu.addItem(LIST_FUTURE_EXAMS, "List future exams", new ListFutureExamsUI()::show);
        menu.addItem(TAKE_EXAM, "Take exam", this.takeExamUI);
        menu.addItem(LIST_GRADES, "List grades", new ListGradesUI()::show);
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildFormativeExamMenu() {
        final Menu menu = new Menu("Formative Exams");

        menu.addItem(TAKE_FORMATIVE_EXAM, "Take formative exam", this.takeFormativeExamUI);
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildMeetingMenu() {
        final Menu menu = new Menu("Meeting");

        menu.addItem(SCHEDULE_MEETING, "Schedule a meeting", new ScheduleMeetingUI()::show);
        menu.addItem(ACCEPT_REJECT_MEETING_REQUEST, "Accept/Reject a meeting request",
                new AcceptRejectMeetingRequestUI()::show);
        menu.addItem(LIST_MEETING_PARTICIPANTS, "List meeting participants", new ListMeetingParticipantsUI()::show);
        menu.addItem(CANCEL_MEETING, "Cancel a meeting", new CancelMeetingUI()::show);
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildBoardMenu() {
        final Menu menu = new Menu("Boards");

        menu.addItem(CREATE_BOARD_OPTION, "Create Board", new CreateBoardUI()::show);
        menu.addItem(LIST_BOARD_OPTION, "List Boards", new ListBoardUI()::show);;
        menu.addItem(CREATE_POSTIT_OPTION, "Create PostIt", new CreatePostItUI()::show);
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }
}
