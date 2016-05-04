package main.java.handlers.responseobjects;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DucNguyenMinh
 * @since 04/05/16
 */
public class PawnsResponseObj
{
    private static final String BOARD_BASE_PATH = "/boards";
    private final List<String> pawnsIds;

    public PawnsResponseObj(String gameId,List<String> pawnsIds)
    {
        this.pawnsIds = getResponseIds(gameId,pawnsIds);
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
}
