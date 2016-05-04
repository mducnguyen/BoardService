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

    public PositionResponseObj(String pawn, int position)
    {
        this.pawn = pawn;
        this.position = position;
    }
}
