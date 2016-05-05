package main.java.handlers.responseobjects;

import lombok.Data;

/**
 * @author DucNguyenMinh
 * @since 03/05/16
 */
@Data
public class PositionResponseObj
{
    private String pawn;

    private int position;

    private PositionResponseObj(){}

    public PositionResponseObj(String boardId,String pawn, int position)
    {
        this.pawn = "/boards/"+boardId+"/pawns/"+pawn;
        this.position = position;
    }
}
