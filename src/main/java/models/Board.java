package main.java.models;

import main.java.logiccontroller.BoardController;
import main.java.logiccontroller.Field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author DucNguyenMinh
 * @since 03/05/16
 */
public class Board
{
    private String id;
    private String game;
    private List<Field> fields;
    private Map<String, Integer> positions;
    private BoardController boardController;
    public Board(String game, List<Field> fields, Map<String,Integer> positions)
    {
        this.game = game;
        String[] strArr =  game.split("/");
        this.id = strArr[strArr.length-1];
        this.positions = positions;
        this.fields = fields;
    }

    public String getGame()
    {
        return game;
    }

    public String getId()
    {
        return id;
    }

    public List<String> getPawns() {

        List<String> pawns = new ArrayList<>();

        for (Map.Entry<String,Integer> entry : positions.entrySet()
             ) {
            pawns.add(entry.getKey());
        }

        return pawns;
    }

    public List<Field> getFields()
    {
        return fields;
    }

    public Map<String, Integer> getPositions()
    {
        return Collections.unmodifiableMap(positions);
    }

    public void updatePosition(String pawn, int position){
        positions.put(pawn,position);
    }

    public void removePawn(String pawn){
        positions.remove(pawn);
        for (Field field : fields) {
            List<String> pawnIds = field.getPawns();
            pawnIds.remove(pawn);
        }
    }

    public int getPositionOfPawn(String pawn)
    {
        return positions.get(pawn);
    }

    public void addField(Field field){
        fields.add(field);
    }
}
