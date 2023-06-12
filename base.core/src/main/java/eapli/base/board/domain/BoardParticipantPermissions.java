package eapli.base.board.domain;

public enum BoardParticipantPermissions {
        WRITE {
                @Override
                public String toString() {
                        return "Write";
                }
        },
        READ {
                @Override
                public String toString() {
                        return "Read";
                }
        },

}
