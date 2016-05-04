package main.java.handlers.responseobjects;

import lombok.Data;
import main.java.models.Board;
import main.java.models.Field;
import main.java.models.Pawn;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author DucNguyenMinh
 * @since 03/05/16
 */
@Data
public class BoardResponseObj
{
    private static String BOARD_BASE_PATH = "/boards";
    private String id;
    private String game;
    private List<Field> fields;
    private List<String> pawns;
    private List<PositionResponseObj> positions;

    private BoardResponseObj()
    {
    }

    public BoardResponseObj(Board board)
    {
        this.id = BOARD_BASE_PATH + "/" + board.getId();
        this.game = board.getGame();
        this.positions = getPositions(board);
        this.pawns = getPawns(board);
        this.fields = getFields(board);
    }

    private List<String> getPawns(Board board)
    {
        if(board.getPawns() != null) {
            List<String> pawnIds = new ArrayList<>();
            Map<String, Integer> positions = board.getPositions();
            for (Map.Entry<String, Integer> entry : positions.entrySet()
                    ) {
                pawnIds.add(entry.getKey());
            }
            return pawnIds;
        }else {
            return new ArrayList<>();
        }
    }

    private List<Field> getFields(Board board)
    {
        if(board.getFields() != null) {
            return board.getFields();
        } else {
            return new ArrayList<>();
        }
    }

    private List<PositionResponseObj> getPositions(Board board)
    {
        if(board.getPositions() != null) {
            List<PositionResponseObj> positionResponses = new ArrayList<>();
            Map<String, Integer> positions = board.getPositions();
            for (Map.Entry<String, Integer> entry : positions.entrySet()
                    ) {
                PositionResponseObj posRes = new PositionResponseObj(entry.getKey(), entry.getValue());
                positionResponses.add(posRes);
            }
            return positionResponses;
        }else {
            return new ArrayList<>();
        }

    }


}
