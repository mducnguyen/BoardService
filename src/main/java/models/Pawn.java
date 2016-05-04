package main.java.models;

/**
 * @author DucNguyenMinh
 * @since 03/05/16
 */
public class Pawn
{
    private String id;

    private String board;

    private String place;

    private String player;

    private String roll;

    private String move;

    public Pawn(String board, String place, String player, String roll, String move)
    {
        this.board = board;
        this.place = place;
        this.player = player;
        this.roll = roll;
        this.move = move;
        String[] strArr = player.split("/");
        this.id = strArr[strArr.length-1];
    }

    public String getId()
    {
        return id;
    }

    public String getBoard()
    {
        return board;
    }

    public String getPlace()
    {
        return place;
    }

    public String getPlayer()
    {
        return player;
    }

    public String getRoll()
    {
        return roll;
    }

    public String getMove()
    {
        return move;
    }

    public void setRoll(String roll)
    {
        this.roll = roll;
    }

    public void setMove(String move)
    {
        this.move = move;
    }

    public void setPlace(String place)
    {
        this.place = place;
    }
}
