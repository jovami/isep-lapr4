package eapli.base.examresult.domain;

public enum ExamGradeProperties {
    NONE {
        @Override
        public String toString() {
            return "Grade not available";
        }
    },
    ONSUBMISSION {
        @Override
        public String toString() {
            return "Grade available on submission";
        }
    },
    AFTERCLOSING {
        @Override
        public String toString() {
            return "Grade available after closing of the exam";
        }
    },
}
