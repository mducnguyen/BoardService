package main.java.models.repositories;

import main.java.models.Board;
import main.java.models.repositories.exceptions.CannotCreateException;

import java.util.List;

/**
 * @author DucNguyenMinh
 * @since 03/05/16
 */
public interface IBoardRepository extends Repository
{

    List<Board> getAllBoards();

    Board createBoard(Board board) throws CannotCreateException;

    Board findBoard(String gameid);

    Board UpdateOrCreateBoard(Board board);

    void deleteBoard(Board board);
}
