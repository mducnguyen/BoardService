package main.java.models;

import sun.security.krb5.internal.PAEncTSEnc;

import java.util.ArrayList;
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
        return positions;
    }

    public void updatePosition(String pawn, int position){
        positions.put(pawn,position);
    }

    public int getPositionOfPawn(String pawn)
    {
        return positions.get(pawn);
    }
}
