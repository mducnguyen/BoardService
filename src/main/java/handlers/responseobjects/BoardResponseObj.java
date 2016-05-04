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
            List<String> pawnResponseObjs = new ArrayList<>();
            Map<Pawn, Integer> positions = board.getPositions();
            for (Map.Entry<Pawn, Integer> entry : positions.entrySet()
                    ) {
                PawnResponseObj pawnResonseObj = new PawnResponseObj(entry.getKey(), board.getId());
                pawnResponseObjs.add(pawnResonseObj.getId());
            }
            return pawnResponseObjs;
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
            Map<Pawn, Integer> positions = board.getPositions();
            for (Map.Entry<Pawn, Integer> entry : positions.entrySet()
                    ) {
                PawnResponseObj pawnResonseObj = new PawnResponseObj(entry.getKey(), board.getId());
                PositionResponseObj posRes = new PositionResponseObj(pawnResonseObj.getId(), entry.getValue());
                positionResponses.add(posRes);
            }
            return positionResponses;
        }else {
            return new ArrayList<>();
        }

    }


}
