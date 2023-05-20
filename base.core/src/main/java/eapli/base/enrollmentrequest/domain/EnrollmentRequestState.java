package eapli.base.enrollmentrequest.domain;

public enum EnrollmentRequestState {
    APPROVED {
        @Override
        public String toString() {
            return "Enrollment request approved";
        }
    },
    DENIED {
        @Override
        public String toString() {
            return "Enrollment request denied";
        }
    },
    PENDING {
        @Override
        public String toString() {
            return "Enrollment request pending";
        }
    },
}
