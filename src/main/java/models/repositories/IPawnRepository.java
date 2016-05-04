package main.java.models.repositories;

import main.java.models.Pawn;

/**
 * @author DucNguyenMinh
 * @since 04/05/16
 */
public interface IPawnRepository extends Repository
{
    Pawn findPawn(String pawnId);

    void deletePawn(Pawn pawn);
}
