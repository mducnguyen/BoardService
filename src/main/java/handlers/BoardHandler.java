package main.java.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.handlers.requestforms.BoardForm;
import main.java.handlers.requestforms.FieldForm;
import main.java.handlers.requestforms.PawnForm;
import main.java.handlers.requestforms.PositionForm;
import main.java.handlers.responseobjects.BoardResponseObj;
import main.java.handlers.responseobjects.BoardsResponseObj;
import main.java.handlers.responseobjects.PawnResponseObj;
import main.java.handlers.responseobjects.PawnsResponseObj;
import main.java.models.Board;
import main.java.models.Field;
import main.java.models.Pawn;
import main.java.models.repositories.*;
import main.java.models.repositories.exceptions.AlreadyExistException;
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
                pawnRepo.createPawnListForBoard(board);
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
            pawnRepo.createPawnListForBoard(board);
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

        for (String pawnUri: pawnIds
             ) {
            String[] idArr = pawnUri.split("/");
            String gameId= idArr[idArr.length-3];
            String pawnId= idArr[idArr.length-1];
            Pawn pawn = pawnRepo.findPawn(gameId,pawnId);
            if (pawn != null){
                pawns.add(pawn);
            }
        }
        return pawns;
    }

    private Map<String,Integer> getPositionMapping(List<PositionForm> positions)
    {
        Map<String, Integer> postionsMap = new HashMap<>();

        for (PositionForm posform: positions
             ) {
            String[] idArr = posform.getPawn().split("/");
            String gameId= idArr[idArr.length-3];
            String pawnId= idArr[idArr.length-1];
            Pawn pawn = pawnRepo.findPawn(gameId,pawnId);
            if (pawn != null){
                postionsMap.put(pawn.getId(), posform.getPosition());
            }
        }

        return  postionsMap;
    }

    public String deleteBoard(String boardId, Request request, Response response)
    {
        Board board = boardRepo.findBoard(boardId);
        if (board != null){
            for (String pawnId: board.getPawns()
                 ) {
                pawnRepo.deletePawn(boardId,pawnId);
            }
            pawnRepo.deleteBoard(boardId);
            boardRepo.deleteBoard(board);
        } else {
            response.status(HttpStatus.SC_NOT_FOUND);
        }

        return "Board was deleted";
    }

    public PawnsResponseObj getAllPawnsForBoard(String gameId, Request request, Response response)
    {
        List<Pawn> pawnList =  pawnRepo.findPawnsForBoard(gameId);
        List<String> pawnIds = new ArrayList<>();
        if(pawnList != null && pawnList.size() != 0){
            for (Pawn pawn: pawnList
                 ) {
                pawnIds.add(pawn.getId());
            }
        }
        PawnsResponseObj responseObj = new PawnsResponseObj(gameId, pawnIds);
        response.status(HttpStatus.SC_OK);
        return responseObj;
    }

    public PawnResponseObj getPawnForBoard(String gameId, String pawnId, Request request, Response response)
    {
        Board board = boardRepo.findBoard(gameId);

        if(board == null){
            response.status(HttpStatus.SC_NOT_FOUND);
            return null;
        }

        Pawn pawn = pawnRepo.findPawn(gameId,pawnId);

        if(pawn == null){
            response.status(HttpStatus.SC_NOT_FOUND);
            return null;
        }

        response.status(HttpStatus.SC_OK);
        return new PawnResponseObj(board,pawn);
    }

    public PawnResponseObj createPawnForBoard(String boardId, Request request, Response response)
    {
        Board board = boardRepo.findBoard(boardId);

        if(board == null){
            response.status(HttpStatus.SC_NOT_FOUND);
            return null;
        }

        PawnForm pawnForm;

        try {
            pawnForm = new ObjectMapper().readValue(request.body(),PawnForm.class);
            Pawn pawn = new Pawn(board.getId(),pawnForm.getPlace(),pawnForm.getPlayer(),pawnForm.getRoll(),pawnForm.getRoll());
            pawnRepo.addPawnToBoard(boardId,pawn);
            board.updatePosition(pawn.getId(), pawnForm.getPosition());
            response.status(HttpStatus.SC_CREATED);

            PawnResponseObj responseObj = new PawnResponseObj(board,pawn);
            return  responseObj;
        } catch (IOException e) {
            e.printStackTrace();
            response.status(HttpStatus.SC_BAD_REQUEST);
        } catch (AlreadyExistException | CannotCreateException e ) {
            response.status(HttpStatus.SC_CONFLICT);
        }

        return null;
    }

    public PawnResponseObj saveOrCreatePawnForBoard(String boardId, String pawnId, Request request, Response response)
    {
        Board board = boardRepo.findBoard(boardId);

        if(board == null){
            response.status(HttpStatus.SC_NOT_FOUND);
            return null;
        }

        Pawn pawn = pawnRepo.findPawn(boardId,pawnId);

        if(pawn == null){
            return createPawnForBoard(boardId,request,response);
        }

        PawnForm pawnForm;

        try {
            pawnForm = new ObjectMapper().readValue(request.body(),PawnForm.class);
            pawn.setMove(pawnForm.getMove());
            pawn.setPlace(pawnForm.getPlace());
            pawn.setRoll(pawnForm.getRoll());
            board.updatePosition(pawn.getId(),pawnForm.getPosition());

            response.status(HttpStatus.SC_OK);
            return new PawnResponseObj(board,pawn);
        } catch (IOException e) {
            response.status(HttpStatus.SC_BAD_REQUEST);
            e.printStackTrace();
        }

        return null;
    }

    public String deletePawnForBoard(String boardId, String pawnId, Request request, Response response)
    {
        Board board = boardRepo.findBoard(boardId);

        if(board == null){
            response.status(HttpStatus.SC_NOT_FOUND);
            return "Board does not exists";
        }


        if(pawnRepo.findPawn(boardId,pawnId) == null){
            response.status(HttpStatus.SC_NOT_FOUND);
            return "Pawn does not exists";
        }

        pawnRepo.deletePawn(boardId,pawnId);
        response.status(HttpStatus.SC_OK);
        return "Pawn was deleted";
    }
}
