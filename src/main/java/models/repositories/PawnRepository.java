package main.java.models.repositories;

import main.java.models.Pawn;

import java.util.Map;

/**
 * @author DucNguyenMinh
 * @since 04/05/16
 */
public class PawnRepository implements IPawnRepository
{
    private final Map<String, Pawn> pawnsMap;

    public PawnRepository(Map<String, Pawn> pawnsMap)
    {
        this.pawnsMap = pawnsMap;
    }

    @Override
    public Pawn findPawn(String pawnId)
    {
        return pawnsMap.get(pawnId);
    }

    @Override
    public void deletePawn(Pawn pawn)
    {
        pawnsMap.remove(pawn.getId());
    }
}
