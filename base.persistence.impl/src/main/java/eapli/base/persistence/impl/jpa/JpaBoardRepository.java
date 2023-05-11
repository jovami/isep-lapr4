package eapli.base.persistence.impl.jpa;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardTitle;
import eapli.base.board.repositories.BoardRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;


public class JpaBoardRepository extends BaseJpaRepositoryBase<Board,Long,Integer> implements BoardRepository {

    JpaBoardRepository(String persistenceUnitName) {
        super(persistenceUnitName, "BoardTitle");
    }

    @PersistenceContext
    EntityManager manager;
    /*protected EntityManager entityManager() {
        if (this.manager == null || !this.manager.isOpen()) {
            this.manager = this.entityManagerFactory().createEntityManager();
        }

        return this.manager;
    }*/

    public boolean hasCellPostIt(int id){
        return false;
        /*Query queryHasPostIt= entityManager().createQuery(
                "select count(c) from Cell c where c.postIt = :cellId",Board.class
        );
        queryHasPostIt.setParameter("cellId",id);
        Object result = queryHasPostIt.getSingleResult();

        manager.close();
        return result == null;*/
    }

    public boolean isBoardTitleUnique(String otherBoardTitle){
        return false;
        /*Query queryHasPostIt= manager.createQuery(
                "select count(b.boardTitle) from Board b where b.boardTitle = :boardTitle",Board.class
        );
        queryHasPostIt.setParameter("boardTitle",otherBoardTitle);
        Object result = queryHasPostIt.getSingleResult();

        manager.close();
        return result == null;*/
    }

    @Override
    public Optional<Board> ofIdentity(BoardTitle id) {
        return Optional.empty();
    }

    @Override
    public void deleteOfIdentity(BoardTitle entityId) {

    }
}
