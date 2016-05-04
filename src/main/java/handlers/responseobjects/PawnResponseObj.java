package main.java.handlers.responseobjects;

import main.java.models.Pawn;

/**
 * @author DucNguyenMinh
 * @since 03/05/16
 */
public class PawnResponseObj
{
    private static final String BOARD_BASE_PATH = "/boards";

    private String id;

    public String getId()
    {
        return id;
    }

    public PawnResponseObj(Pawn pawn, String gameId)
    {
        this.id = BOARD_BASE_PATH+"/"+ gameId+"/pawns/" +pawn.getId();
    }
}
