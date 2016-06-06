package models;

import logiccontroller.BoardController;
import logiccontroller.Field;
import logiccontroller.Throw;
import logiccontroller.exceptions.PlayerNotInturnException;
import models.repositories.exceptions.AlreadyExistException;
import models.repositories.exceptions.CannotCreateException;

import java.io.IOException;
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
    private BoardController boardController;

    public Board(String game, BoardController boardController)
    {
        this.game = game;
        String[] strArr = game.split("/");
        this.id = strArr[strArr.length - 1];
        this.boardController = boardController;
    }

    public String getGame()
    {
        return game;
    }

    public String getId()
    {
        return id;
    }

    public List<String> getPawns()
    {
        return boardController.getPawns();
    }

    public List<Field> getFields()
    {
        return boardController.getFields();
    }

    public Map<String, Integer> getPositions()
    {
        return boardController.getPositions();
    }

    public void updatePosition(Pawn pawn, int position) throws AlreadyExistException, CannotCreateException
    {
        boardController.updatePosition(pawn, position);
    }

    public void removePawn(String pawn)
    {
        boardController.removePawn(pawn);
    }

    public int getPositionOfPawn(String pawn)
    {
        return boardController.getPositionOfPawn(pawn);
    }

    public void addField(Field field)
    {
        boardController.addField(field);
    }

    public void deletePawn(String pawnId)
    {
        boardController.removePawn(pawnId);
    }

    public Pawn findPawn(String pawnId)
    {
        return boardController.findPawn(pawnId);
    }

    public Place findPlace(String placeId)
    {
        return boardController.findPlace(placeId);
    }

    public void movePawn(Pawn pawn, int move)
    {
        boardController.movePawn(pawn,move);
    }

    public String getPlaceOfPawn(Pawn pawn)
    {
        return boardController.getPlaceOfPawn(pawn);
    }

    public Pawn addPawn(Pawn pawn, int position) throws AlreadyExistException, CannotCreateException
    {
        return boardController.addPawnToPosition(pawn,position);
    }


    public Pawn addPawn(Pawn pawn) throws AlreadyExistException, CannotCreateException
    {
        return boardController.addPawn(pawn);
    }

    public void throwTheDices(String playerId) throws IOException
    {
        boardController.throwTheDices(playerId);
    }

    public List<Throw> getThrowsOfPawn(String pawnId)
    {
        return boardController.getThowsOfPawn(pawnId);
    }

    public void checkTurnOfPlayer(String playerId) throws PlayerNotInturnException
    {
        boardController.checkTurmOfPlayer(playerId);
    }
}
