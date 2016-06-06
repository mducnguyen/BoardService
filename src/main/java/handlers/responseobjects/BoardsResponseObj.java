package handlers.responseobjects;

import lombok.Data;
import models.Board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author DucNguyenMinh
 * @since 03/05/16
 */
@Data
public class BoardsResponseObj implements Serializable
{
    private static final String BOARD_BASE_PATH = "/boards";
    private final List<String> boardIds;

    public BoardsResponseObj(List<String> boardIds)
    {
        this.boardIds = getResponseIds(boardIds);
    }

    private List<String> getResponseIds(List<String> boardIds)
    {
        List<String> responseIds = new ArrayList<>();
        for (String id : boardIds
                ) {
            responseIds.add(BOARD_BASE_PATH+"/"+id);
        }

        return responseIds;
    }
}
