package eapli.base.examresult.domain;

public enum ExamFeedbackProperties {
    NONE {
        @Override
        public String toString() {
            return "Feedback not available";
        }
    },
    ON_SUBMISSION {
        @Override
        public String toString() {
            return "Feedback available on submission";
        }
    },
    AFTER_CLOSING {
        @Override
        public String toString() {
            return "Feedback available after closing of the exam";
        }
    },
}
