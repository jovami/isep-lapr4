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
package eapli.base.app.teacher.console.presentation;

import eapli.base.Application;
import eapli.base.app.common.console.AcceptRejectMeetingRequestUI;
import eapli.base.app.common.console.CancelMeetingUI;
import eapli.base.app.common.console.ScheduleMeetingUI;
import eapli.base.app.common.console.presentation.ListMeetingParticipantsUI;
import eapli.base.app.common.console.presentation.authz.CreateBoardUI;
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

public class MainMenu extends AbstractUI {

    private static final String RETURN_LABEL = "Return ";

    private static final int EXIT_OPTION = 0;

    // Course
    private static final int LIST_COURSES = 1;

    // LECTURE
    private static final int SCHEDULE_LECTURE_OPTION = 1;
    private static final int SCHEDULE_EXTRAORDINARY_LECTURE_OPTION = 2;
    private static final int UPDATE_SCHEDULE_OF_LECTURE_OPTION = 3;

    // REGULAR EXAM
    private static final int CREATE_REGULAR_EXAM_OPTION = 1;
    private static final int LIST_EXAMS_COURSE = 2;
    private static final int UPDATE_REGULAR_EXAM_OPTION = 3;
    private static final int LIST_OF_THE_GRADES_OF_EXAMS_OF_MY_COURSES = 4;

    // FORMATIVE EXAM
    private static final int CREATE_FORMATIVE_EXAM = 1;
    private static final int ADD_QUESTION = 2;

    // BOARD
    private static final int CREATE_BOARD_OPTION = 1;
    private static final int LIST_BOARD_OPTION = 2;

    // MEETING
    private static final int SCHEDULE_MEETING = 1;
    private static final int ACCEPT_REJECT_MEETING_REQUEST = 2;
    private static final int LIST_MEETING_PARTICIPANTS = 3;
    private static final int CANCEL_MEETING = 4;
    private static final String SEPARATOR_LABEL = "--------------";

    // SETTINGS
    private static final int MY_USER_OPTION = 1;

    private static final int COURSE_OPTION = 2;
    private static final int REGULAR_EXAM_OPTION = 3;
    private static final int FORMATIVE_EXAM_OPTION = 4;
    private static final int BOARD_OPTION = 5;
    private static final int MEETING_OPTION = 6;
    private static final int LECTURE_OPTION = 7;

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

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

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.POWER_USER, BaseRoles.TEACHER)) {
            mainMenu.addSubMenu(COURSE_OPTION, buildCourseMenu());
            mainMenu.addSubMenu(REGULAR_EXAM_OPTION, buildRegularExamMenu());
            mainMenu.addSubMenu(FORMATIVE_EXAM_OPTION, buildFormativeExamMenu());
            mainMenu.addSubMenu(BOARD_OPTION, buildBoardMenu());
            mainMenu.addSubMenu(MEETING_OPTION, buildMeetingMenu());
            mainMenu.addSubMenu(LECTURE_OPTION, buildLectureMenu());
        }

        if (!Application.settings().isMenuLayoutHorizontal())
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));

        mainMenu.addItem(EXIT_OPTION, "Exit", new ExitWithMessageAction("Bye, Bye"));

        return mainMenu;
    }

    private Menu buildCourseMenu() {
        final Menu menu = new Menu("Course");

        menu.addItem(LIST_COURSES, "List available courses", new ListAvailableCoursesTeacherUI()::show);
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildRegularExamMenu() {
        final Menu menu = new Menu("Regular Exam");

        menu.addItem(CREATE_REGULAR_EXAM_OPTION, "Add regular exam", new CreateRegularExamUI()::show);
        menu.addItem(LIST_EXAMS_COURSE, "List exams in a course", new ListExamsInCourseUI()::show);
        menu.addItem(UPDATE_REGULAR_EXAM_OPTION, "Update regular exam", new UpdateRegularExamUI()::show);
        menu.addItem(LIST_OF_THE_GRADES_OF_EXAMS_OF_MY_COURSES, "List of the grades of exams of my courses",
                new ListExamsGradesInCourseUI()::show);
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildFormativeExamMenu() {
        final Menu menu = new Menu("Formative Exam");

        menu.addItem(CREATE_FORMATIVE_EXAM, "Create formative exam", new CreateFormativeExamUI()::show);
        menu.addItem(ADD_QUESTION, "Add formative exam questions", new AddExamQuestionsUI()::show);
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildBoardMenu() {
        final Menu menu = new Menu("Boards");

        menu.addItem(CREATE_BOARD_OPTION, "Create Board", new CreateBoardUI()::show);
        menu.addItem(LIST_BOARD_OPTION, "List Boards", new ListBoardUI()::show);
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

    private Menu buildLectureMenu() {
        final Menu menu = new Menu("Lecture");

        menu.addItem(SCHEDULE_LECTURE_OPTION, "Schedule a Lecture", new ScheduleLectureUI()::show);
        menu.addItem(SCHEDULE_EXTRAORDINARY_LECTURE_OPTION, "Schedule an Extraordinary Lecture",
                new ScheduleExtraLectureUI()::show);
        menu.addItem(UPDATE_SCHEDULE_OF_LECTURE_OPTION, "Update the Schedule of Lecture",
                new UpdateScheduleLectureUI()::show);
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }
}
