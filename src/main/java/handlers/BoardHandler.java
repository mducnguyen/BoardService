package main.java.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import main.java.handlers.requestforms.BoardForm;
import main.java.handlers.requestforms.FieldForm;
import main.java.handlers.requestforms.PositionForm;
import main.java.handlers.responseobjects.BoardResponseObj;
import main.java.handlers.responseobjects.BoardsResponseObj;
import main.java.models.Board;
import main.java.models.Field;
import main.java.models.Pawn;
import main.java.models.repositories.*;
import main.java.models.repositories.exceptions.CannotCreateException;
import org.apache.http.HttpStatus;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DucNguyenMinh
 * @since 03/05/16
 */
public class BoardHandler
{
    private final IBoardRepository boardRepo;

    private final IPawnRepository pawnRepo;

    private final IFieldRepository fieldRepo;

    public BoardHandler(IBoardRepository boardRepo, IPawnRepository pawnRepo, IFieldRepository fieldRepo)
    {
        this.boardRepo = boardRepo;
        this.pawnRepo = pawnRepo;
        this.fieldRepo = fieldRepo;
    }

    public BoardsResponseObj getAllBoards(Request request, Response response)
    {
        List<Board> boardList = boardRepo.getAllBoards();
        List<String> boardIds = new ArrayList<>();

        for (Board board: boardList) {
            boardIds.add(board.getId());
        }

        response.status(HttpStatus.SC_OK);

        return new BoardsResponseObj(boardIds);
    }

    public BoardResponseObj createBroad(Request request, Response response)
    {
        BoardForm boardForm;

        try {
            boardForm = (new ObjectMapper()).readValue(request.body(), BoardForm.class);
            Board board = new Board(boardForm.getGame(), new ArrayList<>(),new HashMap<>());
            try {
                boardRepo.createBoard(board);
                response.status(HttpStatus.SC_CREATED);
                return new BoardResponseObj(board);
            }catch (CannotCreateException e){
                response.status(HttpStatus.SC_CONFLICT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.status(HttpStatus.SC_BAD_REQUEST);
        }

        return null;
    }

    public BoardResponseObj findBoard(String gameid,Request request, Response response)
    {
        Board board = boardRepo.findBoard(gameid);

        if(board == null ){
            response.status(HttpStatus.SC_NOT_FOUND);
            response.body("There is no such board!");
            return null;
        } else {
            BoardResponseObj responseObj = new BoardResponseObj(board);
            response.status(HttpStatus.SC_OK);
            return responseObj;
        }

    }

    public BoardResponseObj saveOrCreateBoard(String gameid, Request request, Response response)
    {
        BoardResponseObj responseObj = null;
        try {
            BoardForm boardForm = (new ObjectMapper()).readValue(request.body(), BoardForm.class);
            Board board = new Board(boardForm.getGame(),getFields(boardForm.getFields()),getPositionMapping(boardForm.getPositions()));
            boardRepo.saveOrUpdateBoard(board);
            responseObj = new BoardResponseObj(board);
            response.status(HttpStatus.SC_OK);
        } catch (IOException e) {
            response.status(HttpStatus.SC_BAD_REQUEST);
            e.printStackTrace();
        }

        return responseObj;
    }

    private List<Field> getFields(List<FieldForm> fields)
    {
        List<Field> fieldsList = new ArrayList<>();
        for (FieldForm form: fields
             ) {
            Field field = new Field(form.getPlace(),getPawns(form.getPawns()));
            fieldRepo.updateOrCreate(field);
            fieldsList.add(field);
        }

        return fieldsList;
    }

    private List<Pawn> getPawns(List<String> pawnIds)
    {
        List<Pawn> pawns = new ArrayList<>();

        for (String pawnId: pawnIds
             ) {
            Pawn pawn = pawnRepo.findPawn(pawnId);
            if (pawn != null){
                pawns.add(pawn);
            }
        }
        return pawns;
    }

    private Map<Pawn,Integer> getPositionMapping(List<PositionForm> positions)
    {
        Map<Pawn, Integer> postionsMap = new HashMap<>();

        for (PositionForm posform: positions
             ) {
            String[] strArr = posform.getPawn().split("/");
            String pawnId = strArr[strArr.length-1];

            Pawn pawn = pawnRepo.findPawn(pawnId);
            if (pawn != null){
                postionsMap.put(pawn, posform.getPosition());
            }
        }

        return  postionsMap;
    }

    public String deleteBoard(String gameid, Request request, Response response)
    {
        Board board = boardRepo.findBoard(gameid);
        if (board != null){
            for (Pawn pawn: board.getPawns()
                 ) {
                pawnRepo.deletePawn(pawn);
            }
            boardRepo.deleteBoard(board);
        } else {
            response.status(HttpStatus.SC_NOT_FOUND);
        }

        return "Board was deleted";
    }
}
