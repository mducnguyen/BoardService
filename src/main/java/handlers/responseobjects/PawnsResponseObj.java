package handlers.responseobjects;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DucNguyenMinh
 * @since 04/05/16
 */
@Data
public class PawnsResponseObj
{
    private static final String BOARD_BASE_PATH = "/boards";
    private final List<String> pawns;

    public PawnsResponseObj(String gameId,List<String> pawnsIds)
    {
        this.pawns = getResponseIds(gameId,pawnsIds);
    }

    private List<String> getResponseIds(String gameId,List<String> pawnsIds)
    {
        List<String> responseIds = new ArrayList<>();
        for (String id : pawnsIds
                ) {
            responseIds.add(BOARD_BASE_PATH+"/"+gameId+"/pawns/"+id);
        }

        return responseIds;
    }

    public List<String> getPawns()
    {
        return pawns;
    }
}
