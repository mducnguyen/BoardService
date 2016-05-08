package main.java.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.handlers.requestforms.*;
import main.java.handlers.responseobjects.*;
import main.java.logiccontroller.BoardController;
import main.java.logiccontroller.Throw;
import main.java.logiccontroller.exceptions.PlayerNotInturnException;
import main.java.models.Board;
import main.java.logiccontroller.Field;
import main.java.models.Pawn;
import main.java.models.Place;
import main.java.models.repositories.*;
import main.java.models.repositories.exceptions.AlreadyExistException;
import main.java.models.repositories.exceptions.CannotCreateException;
import org.apache.http.HttpStatus;
import spark.Request;
import spark.Response;

import java.io.*;
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

    private final IPlaceRepository placeRepo;


    public BoardHandler(IBoardRepository boardRepo, IPawnRepository pawnRepo, IPlaceRepository placeRepo)
    {
        this.boardRepo = boardRepo;
        this.pawnRepo = pawnRepo;
        this.placeRepo = placeRepo;
    }

    public BoardsResponseObj getAllBoards(Request request, Response response)
    {
        List<Board> boardList = boardRepo.getAllBoards();
        List<String> boardIds = new ArrayList<>();

        for (Board board : boardList) {
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
            BoardController controller = new BoardController(boardForm.getGame(), new ArrayList<>(), new HashMap<>());
            Board board = new Board(boardForm.getGame(), controller);
            try {
                boardRepo.createBoard(board);
                response.status(HttpStatus.SC_CREATED);
                return new BoardResponseObj(board);
            } catch (CannotCreateException e) {
                response.status(HttpStatus.SC_CONFLICT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.status(HttpStatus.SC_BAD_REQUEST);
        }

        return null;
    }

    public BoardResponseObj findBoard(String gameid, Request request, Response response)
    {
        Board board = boardRepo.findBoard(gameid);

        if (board == null) {
            response.status(HttpStatus.SC_NOT_FOUND);
            response.body("There is no such board!");
            return null;
        } else {
            BoardResponseObj responseObj = new BoardResponseObj(board);
            response.status(HttpStatus.SC_OK);
            return responseObj;
        }

    }

//    public BoardResponseObj saveOrCreateBoard(String gameid, Request request, Response response)
//    {
//        BoardResponseObj responseObj = null;
//        try {
//            BoardForm boardForm = (new ObjectMapper()).readValue(request.body(), BoardForm.class);
////            BoardController controller = new BoardController(boardForm.getGame(), getFields(boardForm.getFields()), getPositionMapping(boardForm.getPositions()));
////            Board board = new Board(boardForm.getGame(), controller);
////            boardRepo.UpdateOrCreateBoard(board);
//
//            responseObj = new BoardResponseObj(board);
//            response.status(HttpStatus.SC_OK);
//        } catch (IOException e) {
//            response.status(HttpStatus.SC_BAD_REQUEST);
//            e.printStackTrace();
//        }
//
//        return responseObj;
//    }

    private List<Field> getFields(List<FieldForm> fields)
    {
        List<Field> fieldList = new ArrayList<>();
        for (FieldForm form : fields
                ) {
            List<String> pawnIds = getPawns(form.getPawns());
            String place = getPlace(form.getPlace());
            if (place != "") {
                Field field = new Field(place, pawnIds);
                fieldList.add(field);
            }
        }

        return fieldList;
    }

    private String getPlace(String placeUri)
    {
        String placeId = "";

        String[] idArr = placeUri.split("/");
        placeId = idArr[idArr.length - 1];
        return placeId;

    }

    private List<String> getPawns(List<String> pawnUris)
    {
        List<String> pawns = new ArrayList<>();

        for (String pawnUri : pawnUris
                ) {
            String[] idArr = pawnUri.split("/");
            String pawnId = idArr[idArr.length - 1];
            pawns.add(pawnId);
        }
        return pawns;
    }

    private Map<String, Integer> getPositionMapping(List<PositionForm> positions)
    {
        Map<String, Integer> postionsMap = new HashMap<>();

        for (PositionForm posform : positions
                ) {
            String[] idArr = posform.getPawn().split("/");
            String pawnId = idArr[idArr.length - 1];
            postionsMap.put(pawnId, posform.getPosition());
        }

        return postionsMap;
    }

    public String deleteBoard(String boardId, Request request, Response response)
    {
        Board board = boardRepo.findBoard(boardId);
        if (board != null) {
            for (String pawnId : board.getPawns()
                    ) {
                board.deletePawn(pawnId);
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
        List<Pawn> pawnList = pawnRepo.findPawnsForBoard(gameId);
        List<String> pawnIds = new ArrayList<>();
        if (pawnList != null && pawnList.size() != 0) {
            for (Pawn pawn : pawnList
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

        if (board == null) {
            response.status(HttpStatus.SC_NOT_FOUND);
            return null;
        }

        Pawn pawn = board.findPawn(pawnId);

        if (pawn == null) {
            response.status(HttpStatus.SC_NOT_FOUND);
            return null;
        }

        response.status(HttpStatus.SC_OK);
        return new PawnResponseObj(board, pawn);
    }

    public PawnResponseObj createPawnForBoard(String boardId, Request request, Response response)
    {
        Board board = boardRepo.findBoard(boardId);

        if (board == null) {
            response.status(HttpStatus.SC_NOT_FOUND);
            return null;
        }

        PawnForm pawnForm;

        try {
            pawnForm = new ObjectMapper().readValue(request.body(), PawnForm.class);
            Pawn pawn = new Pawn(board.getId(),pawnForm.getPlayer(), pawnForm.getRoll(), pawnForm.getRoll());

            if (pawnForm.getPosition() != null) {
                board.addPawn(pawn, Integer.valueOf(pawnForm.getPosition()));
            }

            board.addPawn(pawn);

            response.status(HttpStatus.SC_CREATED);
            PawnResponseObj responseObj = new PawnResponseObj(board, pawn);
            return responseObj;
        } catch (IOException e) {
            e.printStackTrace();
            response.status(HttpStatus.SC_BAD_REQUEST);
        } catch (AlreadyExistException | CannotCreateException e) {
            response.status(HttpStatus.SC_CONFLICT);
        }

        return null;
    }

    public PawnResponseObj saveOrCreatePawnForBoard(String boardId, String pawnId, Request request, Response response)
    {
        Board board = boardRepo.findBoard(boardId);

        if (board == null) {
            response.status(HttpStatus.SC_NOT_FOUND);
            return null;
        }

        Pawn pawn = pawnRepo.findPawn(boardId, pawnId);

        if (pawn == null) {
            return createPawnForBoard(boardId, request, response);
        }

        PawnForm pawnForm;

        try {
            pawnForm = new ObjectMapper().readValue(request.body(), PawnForm.class);
            pawn.setMove(pawnForm.getMove());
            pawn.setRoll(pawnForm.getRoll());

            String newPosition = pawnForm.getPosition();

            pawnRepo.updatePawnForBoard(boardId, pawn);
            if(newPosition != null){
                int pos = Integer.valueOf(newPosition);
                board.updatePosition(pawn,pos);
            }
            response.status(HttpStatus.SC_OK);
            return new PawnResponseObj(board, pawn);
        } catch (IOException e) {
            response.status(HttpStatus.SC_BAD_REQUEST);
            e.printStackTrace();
        } catch (AlreadyExistException | CannotCreateException e) {
            response.status(HttpStatus.SC_CONFLICT);
        }


        return null;
    }

    public String deletePawnForBoard(String boardId, String pawnId, Request request, Response response)
    {
        Board board = boardRepo.findBoard(boardId);

        if (board == null) {
            response.status(HttpStatus.SC_NOT_FOUND);
            return "Board does not exists";
        }


        if (pawnRepo.findPawn(boardId, pawnId) == null) {
            response.status(HttpStatus.SC_NOT_FOUND);
            return "Pawn does not exists";
        }

        board.removePawn(pawnId);
        response.status(HttpStatus.SC_OK);
        return "Pawn was deleted";
    }

    public PlacesResponseObj getAllPlacesForBoard(String boardId, Request request, Response response)
    {
        List<Place> places = placeRepo.findAllPlacesOfBoard(boardId);
        PlacesResponseObj placesResponseObj = new PlacesResponseObj(boardId, places);
        response.status(HttpStatus.SC_OK);
        return placesResponseObj;
    }

    public PlaceResponseObj getPlaceForBoard(String boardId, String placeId, Request request, Response response)
    {

        Board board = boardRepo.findBoard(boardId);

        if (board == null) {
            response.status(HttpStatus.SC_NOT_FOUND);
            return null;
        }

        Place place = board.findPlace(placeId);

        if (place == null) {
            response.status(HttpStatus.SC_NOT_FOUND);
            return null;
        }

        response.status(HttpStatus.SC_OK);
        return new PlaceResponseObj(boardId, place);
    }

    public PlaceResponseObj saveOrCreatePlaceForBoard(String boardId, String placeId, Request request, Response response)
    {

        Board board = boardRepo.findBoard(boardId);

        if (board == null) {
            response.status(HttpStatus.SC_NOT_FOUND);
            return null;
        }

        Place place = placeRepo.findPlace(boardId, placeId);

        if (place == null) {
            return createPlaceForBoard(boardId, request, response);
        }

        PlaceForm placeForm;

        try {
            placeForm = new ObjectMapper().readValue(request.body(), PlaceForm.class);
            place.setName(placeForm.getName());
            place.setBroker(placeForm.getBroker());
            response.status(HttpStatus.SC_OK);
            return new PlaceResponseObj(boardId, place);
        } catch (IOException e) {
            response.status(HttpStatus.SC_BAD_REQUEST);
            e.printStackTrace();
        }

        return null;
    }

    private PlaceResponseObj createPlaceForBoard(String boardId, Request request, Response response)
    {
        Board board = boardRepo.findBoard(boardId);

        if (board == null) {
            response.status(HttpStatus.SC_NOT_FOUND);
            return null;
        }

        PlaceForm placeForm;

        try {
            placeForm = new ObjectMapper().readValue(request.body(), PlaceForm.class);
            Place place = new Place(boardId, placeForm.getName(), placeForm.getBroker());
            placeRepo.addPlaceToBoard(boardId, place);
            board.addField(new Field(place.getId(), new ArrayList<>()));
            response.status(HttpStatus.SC_CREATED);
            PlaceResponseObj responseObj = new PlaceResponseObj(boardId, place);
            return responseObj;
        } catch (IOException e) {
            e.printStackTrace();
            response.status(HttpStatus.SC_BAD_REQUEST);
        } catch (CannotCreateException e) {
            response.status(HttpStatus.SC_CONFLICT);
        }
        return null;
    }

    public BoardResponseObj makeAMoveOfPawnForBoard(String boardId, String pawnId, Request request, Response response)
    {
        Board board = boardRepo.findBoard(boardId);

        if (board == null) {
            response.status(HttpStatus.SC_NOT_FOUND);
            return null;
        }

        Pawn pawn = board.findPawn(pawnId);

        if (pawn == null) {
            response.status(HttpStatus.SC_NOT_FOUND);
            return null;
        }

        MoveForm moveForm;

        try {
            moveForm = new ObjectMapper().readValue(request.body(),MoveForm.class);

            board.movePawn(pawn,moveForm.getMove());

            response.status(HttpStatus.SC_OK);
            return new BoardResponseObj(board);
        } catch (IOException e) {
            response.status(HttpStatus.SC_BAD_REQUEST);
            e.printStackTrace();
        }
        return null;
    }

    public EventsResponseObj rollTheDices(String boardId, String playerId, Request request, Response response) throws InterruptedException
    {

        Board board = boardRepo.findBoard(boardId);

        if (board == null) {
            response.status(HttpStatus.SC_NOT_FOUND);
            return null;
        }

        try {
            board.checkTurnOfPlayer(playerId);
            board.throwTheDices(playerId);
            response.status(HttpStatus.SC_OK);
            return new EventsResponseObj();
        } catch (IOException e) {
            response.status(HttpStatus.SC_BAD_REQUEST);
            e.printStackTrace();
        } catch (PlayerNotInturnException e) {
            response.status(HttpStatus.SC_FORBIDDEN);
            e.printStackTrace();
        }

        return null;
    }


    public ThrowsResponseObj getAllThrowsOfPawn(String boardId, String pawnId, Request request, Response response)
    {

        Board board = boardRepo.findBoard(boardId);

        if (board == null) {
            response.status(HttpStatus.SC_NOT_FOUND);
            return null;
        }

        List<Throw> throwList =  board.getThrowsOfPawn(pawnId);

        response.status(HttpStatus.SC_OK);

        return new ThrowsResponseObj(throwList);
    }
}
