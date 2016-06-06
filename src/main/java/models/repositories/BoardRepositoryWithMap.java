package models.repositories;

import models.Board;
import models.repositories.exceptions.CannotCreateException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author DucNguyenMinh
 * @since 03/05/16
 */
public class BoardRepositoryWithMap implements IBoardRepository
{
    private final Map<String, Board> boardsMap;

    public BoardRepositoryWithMap(Map<String, Board> boardsMap)
    {
        this.boardsMap = boardsMap;
    }

    @Override
    public List<Board> getAllBoards()
    {
        List<Board> boards = new ArrayList<>();

        for (Map.Entry<String, Board> entry :
                boardsMap.entrySet()) {
            boards.add(entry.getValue());
        }

        return boards;
    }

    @Override
    public Board createBoard(Board board) throws CannotCreateException
    {
        if(boardsMap.containsKey(board.getId())) {
            throw new CannotCreateException();
        }
        boardsMap.put(board.getId(), board);
        return boardsMap.get(board.getId());
    }

    @Override
    public Board findBoard(String gameid)
    {
        return boardsMap.get(gameid);
    }

    @Override
    public Board UpdateOrCreateBoard(Board board)
    {
        boardsMap.put(board.getId(),board);
        return board;
    }

    @Override
    public void deleteBoard(Board board)
    {
        boardsMap.remove(board.getId());
    }
}
