package eapli.base.examresult.domain;

public enum ExamFeedbackProperties {
    NONE {
        @Override
        public String toString() {
            return "Feedback not available";
        }
    },
    ONSUBMISSION {
        @Override
        public String toString() {
            return "Feedback available on submission";
        }
    },
    AFTERCLOSING {
        @Override
        public String toString() {
            return "Feedback available after closing of the exam";
        }
    },
}
