package eapli.base.board.application;

public class ShareBoardService {
/*
    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private final TransactionalContext txCtx = PersistenceContext.repositories()
            .newTransactionalContext();

    private ArrayList<SystemUser> users;
    private final BoardRepository boardRepository;

    private final BoardParticipantRepository boardParticipantRepository;

    private final UserRepository userRepository;

    public ShareBoardService(){
        boardRepository = PersistenceContext.repositories().boards();
        boardParticipantRepository = PersistenceContext.repositories().boardParticipants();
        userRepository = PersistenceContext.repositories().users();
        users = (ArrayList<SystemUser>) userRepository.findAll();

    }

    public List<BoardDTO> listBoardsUserOwns() {

        return new BoardMapper().toDTO(this.boardRepository.listBoardsUserOwns(userRepository.ofIdentity(authz.session()
                .orElseThrow()
                .authenticatedUser()
                .identity()).orElseThrow()));
    }

    public boolean shareBoard(BoardDTO board, List<SystemUserNameEmailDTO> users)
    {
        txCtx.beginTransaction();

        for (SystemUserNameEmailDTO user : users) {
            SystemUser us = fromDTO(user);
            BoardParticipant boardParticipant = new BoardParticipant(board.board(), us);
            boardParticipantRepository.save(boardParticipant);
        }

        txCtx.commit();

        return true;

    }

    public List<BoardParticipantDTO> boardParticipants(BoardDTO board)
    {
        return new BoardParticipantMapper().toDTO(boardParticipantRepository.listBoardParticipants(board.board()));
    }

    private SystemUser fromDTO(SystemUserNameEmailDTO dto) throws ConcurrencyException {
        return this.userRepository.ofIdentity(dto.username())
                .orElseThrow(() -> new ConcurrencyException("User no longer exists"));
    }

    public List<SystemUserNameEmailDTO> Users() {
        return new SystemUserNameEmailDTOMapper().toDTO(users, Comparator.comparing(SystemUser::identity));
    }
*/


}