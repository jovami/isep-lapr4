package eapli.base.board.domain;

public enum BoardState {

    /* Board create with only its owner as a member */
    CREATED,

    /* Board has other members in it */
    SHARED,

    /* Board is archived */
    ARCHIVED,
}
