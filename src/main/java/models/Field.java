package main.java.models;

import java.util.List;

/**
 * @author DucNguyenMinh
 * @since 03/05/16
 */
public class Field
{
    private String place;
    private List<Pawn> pawns;

    public Field(String place, List<Pawn> pawns)
    {
        this.place = place;
        this.pawns = pawns;
    }


    public String getPlace()
    {
        return place;
    }

    public List<Pawn> getPawns()
    {
        return pawns;
    }
}
