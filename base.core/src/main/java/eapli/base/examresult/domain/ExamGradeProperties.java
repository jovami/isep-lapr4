package eapli.base.examresult.domain;

public enum ExamGradeProperties {
    NONE {
        @Override
        public String toString() {
            return "Grade not available";
        }
    },
    ON_SUBMISSION {
        @Override
        public String toString() {
            return "Grade available on submission";
        }
    },
    AFTER_CLOSING {
        @Override
        public String toString() {
            return "Grade available after closing of the exam";
        }
    },
}
