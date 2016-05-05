package main.java.handlers.responseobjects;

import lombok.Data;
import main.java.logiccontroller.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DucNguyenMinh
 * @since 05/05/16
 */
@Data
public class FieldResponseObj
{

    private String place;
    private List<String> pawns;

    public FieldResponseObj(String boardId, Field field)
    {
        this.place = "/boards/"+boardId+"/places/"+field.getPlace();
        this.pawns = getPawnsURI(boardId, field.getPawns());
    }

    private List<String> getPawnsURI(String boardId, List<String> pawns)
    {
        List<String> pawnUris = new ArrayList<>();

        for (String pawnId: pawns
             ) {
            pawnUris.add("/boards/"+boardId+"/pawns/"+pawnId);
        }
        return pawnUris;
    }
}
