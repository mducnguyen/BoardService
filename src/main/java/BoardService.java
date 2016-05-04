package main.java;

import main.java.handlers.BoardHandler;
import main.java.models.Board;
import main.java.models.repositories.*;
import main.java.transformer.JsonTransformer;

import java.util.HashMap;

import static spark.Spark.*;

/**
 * @author DucNguyenMinh
 * @since 03/05/16
 */
public class BoardService
{
    public static void main(String args[]){

        registerRepositories();
        JsonTransformer jsonTransformer = new JsonTransformer();

        BoardHandler boardHandler = new BoardHandler(
                (IBoardRepository) RepositoryProvider.getRepository(IBoardRepository.class),
                (IPawnRepository) RepositoryProvider.getRepository(IPawnRepository.class),
                (IFieldRepository) RepositoryProvider.getRepository(IFieldRepository.class)

        );

        //BOARDS
        get("/boards", (request, response) -> boardHandler.getAllBoards(request,response), jsonTransformer );
        post("/boards", ((request, response) -> boardHandler.createBroad(request,response)), jsonTransformer);

        //BOARDS{GAMEID}
        get("/boards/:gameid", (request, response) -> boardHandler.findBoard(request.params(":gameid"),request,response),jsonTransformer);
        put("/boards/:gameid", (request, response) -> boardHandler.saveOrCreateBoard(request.params(":gameid"),request,response),jsonTransformer);
        delete("/boards/:gameid", (request, response) -> boardHandler.deleteBoard(request.params(":gameid"), request,response),jsonTransformer);

        //BOARDS{GAMEID}PAWNS

    }

    private static void registerRepositories(){

        RepositoryProvider.register(IBoardRepository.class, new BoardRepositoryWithMap(new HashMap<>()));
        RepositoryProvider.register(IPawnRepository.class,  new PawnRepository(new HashMap<>()));
        RepositoryProvider.register(IFieldRepository.class,  new FieldRepository(new HashMap<>()));
    }
}
