package main.java.handlers.responseobjects;

import lombok.Data;
import main.java.models.Board;
import main.java.logiccontroller.Field;

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
    private List<FieldResponseObj> fields;
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
            return new PawnsResponseObj(board.getId(),board.getPawns()).getPawns();
        }else {
            return new ArrayList<>();
        }
    }

    private List<FieldResponseObj> getFields(Board board)
    {
        List<FieldResponseObj> fields = new ArrayList<>();
        if(board.getFields() != null) {
            List<Field> fieldList = board.getFields();
            for (Field field: fieldList
                 ) {
                fields.add(new FieldResponseObj(board.getId(), field));
            }
        }

        return fields;
    }

    private List<PositionResponseObj> getPositions(Board board)
    {
        if(board.getPositions() != null) {
            List<PositionResponseObj> positionResponses = new ArrayList<>();
            Map<String, Integer> positions = board.getPositions();
            for (Map.Entry<String, Integer> entry : positions.entrySet()
                    ) {
                PositionResponseObj posRes = new PositionResponseObj(board.getId(),entry.getKey(), entry.getValue());
                positionResponses.add(posRes);
            }
            return positionResponses;
        }else {
            return new ArrayList<>();
        }

    }


}
