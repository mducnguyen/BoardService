package main.java.models.repositories;

import main.java.models.Board;
import main.java.models.Pawn;
import main.java.models.repositories.exceptions.AlreadyExistException;
import main.java.models.repositories.exceptions.CannotCreateException;

import java.util.List;

/**
 * @author DucNguyenMinh
 * @since 04/05/16
 */
public interface IPawnRepository extends Repository
{

    Pawn findPawn(String boardId, String pawnId);

    void deletePawn(String boardId, String pawnId);

    void createPawnListForBoard(String board);

    void deleteBoard(String boardId);

    List<Pawn> findPawnsForBoard(String boardId);

    Pawn addPawnToBoard(String boardId, Pawn pawn) throws CannotCreateException, AlreadyExistException;

    void updatePawnForBoard(String boardId, Pawn pawn);
}
