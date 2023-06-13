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
package eapli.base.app.manager.console.presentation;

import eapli.base.app.common.console.AcceptRejectMeetingRequestUI;
import eapli.base.app.common.console.CancelMeetingUI;
import eapli.base.app.common.console.ScheduleMeetingUI;
import eapli.base.app.common.console.presentation.ListMeetingParticipantsUI;
import eapli.base.app.common.console.presentation.authz.*;
import eapli.base.app.manager.console.presentation.authz.AddUserUI;
import eapli.base.app.manager.console.presentation.authz.DisableUserAction;
import eapli.base.app.manager.console.presentation.authz.EnableUserAction;
import eapli.base.app.manager.console.presentation.authz.ListUsersAction;
import eapli.base.app.manager.console.presentation.clientuser.AcceptRefuseEnrollmentRequestAction;
import eapli.base.app.manager.console.presentation.clientuser.AcceptRefuseSignupRequestAction;
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

    // USERS
    private static final int ADD_USER_OPTION = 1;
    private static final int LIST_USERS_OPTION = 2;
    private static final int DISABLE_USER_OPTION = 3;

    private static final int ENABLE_USER_OPTION = 7;
    private static final int ACCEPT_REFUSE_SIGNUP_REQUEST_OPTION = 4;
    private static final int LIST_TEACHERS_STUDENTS_MANAGERS = 5;
    private static final int ACCEPT_REFUSE_COURSE_APPLICATION_OPTION = 6;

    // Boards
    private static final int CREATE_BOARD_OPTION = 1;
    private static final int LIST_BOARD_OPTION = 2;
    private static final int CREATE_POSTIT_OPTION = 3;

    // COURSE
    private static final int ADD_COURSE_OPTION = 1;
    private static final int LIST_COURSES = 2;
    private static final int OPEN_COURSE = 3;
    private static final int CLOSE_COURSE = 4;
    private static final int OPEN_ENROLLMENTS = 5;
    private static final int CLOSE_ENROLLMENTS = 6;
    private static final int SET_COURSE_TEACHERS = 7;
    private static final int ENROLL_STUDENTS_IN_BULK_CSV = 8;

    // SETTINGS

    private static final int MY_USER_OPTION = 1;
    private static final int USERS_OPTION = 2;
    private static final int BOARD_OPTION = 3;
    private static final int COURSE_OPTION = 4;

    // MEETING
    private static final int MEETING_OPTION = 5;
    private static final int SCHEDULE_MEETING = 1;
    private static final int ACCEPT_REJECT_MEETING_REQUEST = 2;
    private static final int LIST_MEETING_PARTICIPANTS = 3;
    private static final int CANCEL_MEETING = 4;

    private static final String SEPARATOR_LABEL = "--------------";

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

        mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.POWER_USER, BaseRoles.MANAGER)) {
            mainMenu.addSubMenu(USERS_OPTION, buildUsersMenu());
            mainMenu.addSubMenu(BOARD_OPTION, buildBoardMenu());
            mainMenu.addSubMenu(COURSE_OPTION, buildCourseMenu());
            mainMenu.addSubMenu(MEETING_OPTION, buildMeetingMenu());
        }

        mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));

        mainMenu.addItem(EXIT_OPTION, "Exit", new ExitWithMessageAction("Bye, Bye"));

        return mainMenu;
    }

    private Menu buildCourseMenu() {
        final Menu menu = new Menu("Courses");

        menu.addItem(ADD_COURSE_OPTION, "Add course", new CreateCourseUI()::show);
        menu.addItem(LIST_COURSES, "List available courses", new ListAvailableCoursesManagerUI()::show);
        menu.addItem(OPEN_COURSE, "Open a course", new OpenCourseUI()::show);
        menu.addItem(CLOSE_COURSE, "Close a course", new CloseCourseUI()::show);
        menu.addItem(OPEN_ENROLLMENTS, "Open enrollments", new OpenEnrollmentUI()::show);
        menu.addItem(CLOSE_ENROLLMENTS, "Close enrollments", new CloseEnrollmentUI()::show);
        menu.addItem(SET_COURSE_TEACHERS, "Set Staff", new SetCourseTeacherUI()::show);
        menu.addItem(ENROLL_STUDENTS_IN_BULK_CSV, "Enroll students in bulk ,import csv file",
                new CSVLoaderStudentsUI()::show);
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
        return menu;
    }

    private Menu buildUsersMenu() {
        final Menu menu = new Menu("Users");

        menu.addItem(ADD_USER_OPTION, "Add User", new AddUserUI()::show);
        menu.addItem(LIST_USERS_OPTION, "List all Users", new ListUsersAction());
        menu.addItem(DISABLE_USER_OPTION, "Deactivate User", new DisableUserAction());
        menu.addItem(ACCEPT_REFUSE_SIGNUP_REQUEST_OPTION, "Accept/Refuse Signup Request",
                new AcceptRefuseSignupRequestAction());
        menu.addItem(LIST_TEACHERS_STUDENTS_MANAGERS, "List Teachers,Students and Managers",
                new ListTeachersStudentsManagersUI()::show);
        menu.addItem(ACCEPT_REFUSE_COURSE_APPLICATION_OPTION, "Accept/Refuse Course Application",
                new AcceptRefuseEnrollmentRequestAction());
        menu.addItem(ENABLE_USER_OPTION, "Enable User", new EnableUserAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildBoardMenu() {
        final Menu menu = new Menu("Boards");

        menu.addItem(CREATE_BOARD_OPTION, "Create Board", new CreateBoardUI()::show);
        menu.addItem(LIST_BOARD_OPTION, "List Boards", new ListBoardUI()::show);
        menu.addItem(CREATE_POSTIT_OPTION, "Create PostIt", new CreatePostItUI()::show);
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
}
