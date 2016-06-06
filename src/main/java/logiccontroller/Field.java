package logiccontroller;

import models.Pawn;

import java.util.Collections;
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
        return Collections.unmodifiableList(pawns);
    }

    public void addPawn(String pawnId){
        pawns.add(pawnId);
    }

    public void releasePawm(Pawn pawn)
    {
        pawns.remove(pawn.getId());
    }
}
