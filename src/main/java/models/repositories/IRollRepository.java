package main.java.models.repositories;

import main.java.logiccontroller.Throw;

import java.util.List;

/**
 * @author DucNguyenMinh
 * @since 08/05/16
 */
public interface IRollRepository extends Repository
{
    void addThrowToPawn(String boardId, String playerId, Throw thr);

    List<Throw> findAllPawnOf(String boardId, String pawnId);
}
