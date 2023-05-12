package eapli.base.course.domain;

public enum CourseState {
    CLOSE {
        @Override
        public String toString() {
            return "Created";
        }
    },
    OPEN {
        @Override
        public String toString() {
            return "Opened";
        }
    },
    ENROLL {
        @Override
        public String toString() {
            return "Open to enrollments";
        }
    },
    INPROGRESS {
        @Override
        public String toString() {
            return "In progress";
        }
    },
    CLOSED {
        @Override
        public String toString() {
            return "Closed";
        }
    },
}
