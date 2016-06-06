package models.repositories;

import logiccontroller.Throw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author DucNguyenMinh
 * @since 08/05/16
 */
public class RollRepository implements IRollRepository
{

    private Map<String, List<Throw>> rollMap;

    public RollRepository(Map<String, List<Throw>> rollMap)
    {
        this.rollMap = rollMap;
    }

    @Override
    public void addThrowToPawn(String boardId, String playerId, Throw thr)
    {
        String key = "/boards/" + boardId + "/pawns/" + playerId;

        List<Throw> throwList = rollMap.get(key);
        if (throwList == null ) {
            throwList = new ArrayList<>();
            rollMap.put(key, throwList);
        }

        throwList.add(thr);
    }

    @Override
    public List<Throw> findAllPawnOf(String boardId, String pawnId)
    {
        String key = "/boards/" + boardId + "/pawns/" + pawnId;

        return rollMap.get(key) != null? rollMap.get(key) : new ArrayList<>();
    }
}
