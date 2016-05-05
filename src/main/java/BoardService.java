package main.java;

import main.java.handlers.BoardHandler;
import main.java.models.repositories.*;
import main.java.transformer.JsonTransformer;
import spark.Response;

import java.util.HashMap;

import static spark.Spark.*;

/**
 * @author DucNguyenMinh
 * @since 03/05/16
 */
public class BoardService
{
    public static void main(String args[])
    {

        registerRepositories();
        JsonTransformer jsonTransformer = new JsonTransformer();

        BoardHandler boardHandler = new BoardHandler(
                (IBoardRepository) RepositoryProvider.getRepository(IBoardRepository.class),
                (IPawnRepository) RepositoryProvider.getRepository(IPawnRepository.class),
                (IPlaceRepository) RepositoryProvider.getRepository(IPlaceRepository.class)
        );

        //BOARDS
        get("/boards", (request, response) -> boardHandler.getAllBoards(request, response), jsonTransformer);
        post("/boards", ((request, response) -> boardHandler.createBroad(request, response)), jsonTransformer);

        //BOARDS{GAMEID}
        get("/boards/:gameid", (request, response) -> boardHandler.findBoard(request.params(":gameid"), request, response), jsonTransformer);
        put("/boards/:gameid", (request, response) -> boardHandler.saveOrCreateBoard(request.params(":gameid"), request, response), jsonTransformer);
        delete("/boards/:gameid", (request, response) -> boardHandler.deleteBoard(request.params(":gameid"), request, response), jsonTransformer);

        //BOARDS{GAMEID}PAWNS
        get("/boards/:gameid/pawns", (request, response) -> boardHandler.getAllPawnsForBoard(request.params(":gameid"), request, response), jsonTransformer);
        post("/boards/:gameid/pawns", (request, response) -> boardHandler.createPawnForBoard(request.params(":gameid"), request, response), jsonTransformer);

        //BOARDS{GAMEID}PAWNS{PAWNID}
        get("/boards/:gameid/pawns/:pawnid", (request, response) -> boardHandler.getPawnForBoard(request.params(":gameid"), request.params(":pawnid"), request, response), jsonTransformer);
        put("/boards/:gameid/pawns/:pawnid", (request, response) -> boardHandler.saveOrCreatePawnForBoard(request.params(":gameid"), request.params(":pawnid"), request, response), jsonTransformer);
        delete("/boards/:gameid/pawns/:pawnid", (request, response) -> boardHandler.deletePawnForBoard(request.params(":gameid"), request.params(":pawnid"), request, response), jsonTransformer);

        //BOARDS{GAMEID}PAWNS{PAWNID}MOVE

        //BOARDS{GAMEID}PLACES
        get("/boards/:gameid/places", (request, response) -> boardHandler.getAllPlacesForBoard(request.params(":gameid"), request, response), jsonTransformer);

        //BOARDS{GAMEID}PLACES{PLACEID}
        get("/boards/:gameid/places/:place", (request, response) -> boardHandler.getPlaceForBoard(request.params(":gameid"),request.params(":place"),request,response),jsonTransformer);
        put("/boards/:gameid/places/:place", (request, response) -> boardHandler.saveOrCreatePlaceForBoard(request.params(":gameid"),request.params(":place"),request,response),jsonTransformer);
    }

    private static void registerRepositories()
    {

        RepositoryProvider.register(IBoardRepository.class, new BoardRepositoryWithMap(new HashMap<>()));
        RepositoryProvider.register(IPawnRepository.class, new PawnRepository(new HashMap<>()));
        RepositoryProvider.register(IPlaceRepository.class, new PlaceRepository(new HashMap<>()));
    }
}
