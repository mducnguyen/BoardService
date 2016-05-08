package main.java.handlers.responseobjects;

import lombok.Data;
import main.java.models.Board;
import main.java.models.Pawn;

/**
 * @author DucNguyenMinh
 * @since 03/05/16
 */
@Data
public class PawnResponseObj
{
    private static final String BOARD_BASE_PATH = "/boards";

    private String id;

    private String player;

    private String place;

    private int position;

    private String roll;

    private String move;

    public String getId()
    {
        return id;
    }

    public PawnResponseObj(Board board, Pawn pawn)
    {
        this.id = BOARD_BASE_PATH+"/"+ board.getId() +"/pawns/" +pawn.getId();
        this.player = pawn.getPlayer();
        this.place = "/boards/"+board.getId()+"/places/"+board.getPlaceOfPawn(pawn);
        this.position = board.getPositionOfPawn(pawn.getId());
        this.roll = pawn.getRoll();
        this.move = pawn.getMove();
    }



}
