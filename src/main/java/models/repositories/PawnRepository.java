package main.java.models.repositories;

import main.java.models.Board;
import main.java.models.Pawn;
import main.java.models.repositories.exceptions.AlreadyExistException;
import main.java.models.repositories.exceptions.CannotCreateException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DucNguyenMinh
 * @since 04/05/16
 */
public class PawnRepository implements IPawnRepository
{
    private final Map<String, Map<String,Pawn>> pawnsMap;

    public PawnRepository(Map<String, Map<String,Pawn>> pawnsMap)
    {
        this.pawnsMap = pawnsMap;
    }

    @Override
    public Pawn findPawn(String boardId, String pawnId)
    {
        Map<String, Pawn> gamePawnMap = pawnsMap.get(boardId);
        if(gamePawnMap != null){
            return gamePawnMap.get(pawnId);
        }
        return null;
    }

    @Override
    public void deletePawn(String boardId, String pawnid)
    {Map<String, Pawn> gamePawnMap = pawnsMap.get(boardId);
        if(gamePawnMap != null) {
            gamePawnMap.remove(pawnid);
        }
    }

    @Override
    public void createPawnListForBoard(String boardId)
    {
        if (!pawnsMap.containsKey(boardId)) {
            pawnsMap.put(boardId, new HashMap<>());
        }
    }

    @Override
    public void deleteBoard(String boardId)
    {
        pawnsMap.remove(boardId);
    }

    @Override
    public List<Pawn> findPawnsForBoard(String boardId)
    {
        Map<String, Pawn> gamePawnMap = pawnsMap.get(boardId);
        List<Pawn> pawns = new ArrayList<>();
        if(gamePawnMap != null){
            pawns = new ArrayList<>(gamePawnMap.values());
        }
        return pawns;
    }

    @Override
    public Pawn addPawnToBoard(String boardId, Pawn pawn) throws CannotCreateException, AlreadyExistException
    {
        Map<String, Pawn> gamePawnMap = pawnsMap.get(boardId);
        if(gamePawnMap == null){
            throw new CannotCreateException();
        }
        if(gamePawnMap.get(pawn.getId()) != null){
            throw new AlreadyExistException();
        }
        gamePawnMap.put(pawn.getId(),pawn);
        return pawn;
    }

    @Override
    public void updatePawnForBoard(String boardId, Pawn pawn)
    {
        Map<String, Pawn> gamePawnMap = pawnsMap.get(boardId);
        List<Pawn> pawns = new ArrayList<>();
        if(gamePawnMap != null){
            gamePawnMap.put(pawn.getId(),pawn);
        }
    }
}
