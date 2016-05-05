package main.java.logiccontroller;

import java.util.List;

/**
 * @author DucNguyenMinh
 * @since 03/05/16
 */
public class Field
{
    private String place;

    private List<String> pawns;

    public Field(String place, List<String> pawns)
    {
        this.place = place;
        this.pawns = pawns;
    }

    public String getPlace()
    {
        return place;
    }

    public List<String> getPawns()
    {
        return pawns;
    }
}
