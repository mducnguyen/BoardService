package logiccontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import ServiceProvider.DiceServiceProvider;
import ServiceProvider.DiceServiceProvider;
import ServiceProvider.GameServiceProvider;
import ServiceProvider.YellopagesService;
import handlers.requestforms.FieldForm;
import handlers.requestforms.PositionForm;
import handlers.requestforms.RollForm;
import logiccontroller.exceptions.PlayerNotInturnException;
import models.Pawn;
import models.Place;
import models.repositories.IPawnRepository;
import models.repositories.IPlaceRepository;
import models.repositories.IRollRepository;
import models.repositories.RepositoryProvider;
import models.repositories.exceptions.AlreadyExistException;
import models.repositories.exceptions.CannotCreateException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * @author DucNguyenMinh
 * @since 05/05/16
 */
public class BoardController
{
    private final Map<String, Integer> positions;
    private final List<Field> fields;
    private IPawnRepository pawnRepo;
    private IPlaceRepository placeRepo;
    private IRollRepository rollRepo;
    private String boardId;

    public BoardController(String boardId, List<Field> fields, Map<String, Integer> positions)
    {
        String[] strArr = boardId.split("/");
        this.boardId = strArr[strArr.length - 1];
        this.pawnRepo = (IPawnRepository) RepositoryProvider.getRepository(IPawnRepository.class);
        this.pawnRepo.createPawnListForBoard(this.boardId);
        this.placeRepo = (IPlaceRepository) RepositoryProvider.getRepository(IPlaceRepository.class);
        this.placeRepo.createPlaceListForBoard(this.boardId);
        this.rollRepo = (IRollRepository) RepositoryProvider.getRepository(IRollRepository.class);
        this.fields = getValidFields(fields);
        this.positions = getValidPositions(positions);
    }

    public int getPositionOfPawn(String pawn)
    {
        return positions.get(pawn) != null ? positions.get(pawn) : 0;
    }

    public void addField(Field field)
    {
        fields.add(field);
    }


    public void removePawn(String pawn)
    {
        pawnRepo.deletePawn(boardId, pawn);
        positions.remove(pawn);
        for (Field field : fields) {
            List<String> pawnIds = field.getPawns();
            pawnIds.remove(pawn);
        }
    }


    public void updatePosition(Pawn pawn, int position) throws AlreadyExistException, CannotCreateException
    {
        if (pawnRepo.findPawn(boardId, pawn.getId()) == null) {
            pawnRepo.addPawnToBoard(boardId, pawn);
            positions.put(pawn.getId(), 0);
            Field field = fields.get(0);
            if (field != null) field.addPawn(pawn.getId());
        } else {

            Field oldField = fields.get(getPositionOfPawn(pawn.getId()));
            if (oldField != null) {
                oldField.releasePawm(pawn);
                positions.put(pawn.getId(), position);

                Field newField = fields.get(position);
                if (newField != null) {
                    newField.addPawn(pawn.getId());
                }
            }
        }
    }

    public Map<String, Integer> getPositions()
    {
        return Collections.unmodifiableMap(positions);
    }

    public List<Field> getFields()
    {
        return fields;
    }

    public List<String> getPawns()
    {

        List<String> pawns = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : positions.entrySet()
                ) {
            pawns.add(entry.getKey());
        }

        return pawns;
    }

    private List<Field> getValidFields(List<Field> fields)
    {
        List<Field> fieldList = new ArrayList<>();
        for (Field notFilteredfield : fields
                ) {
            List<String> pawnIds = filterPawns(notFilteredfield.getPawns());
            String place = filterPlace(notFilteredfield.getPlace());
            if (place != "") {
                Field field = new Field(place, pawnIds);
                fieldList.add(field);
            }
        }

        return fieldList;
    }

    private List<String> filterPawns(List<String> pawnIds)
    {
        List<String> pawns = new ArrayList<>();

        for (String pawnId : pawnIds
                ) {
            Pawn pawn = pawnRepo.findPawn(boardId, pawnId);
            if (pawn != null) {
                pawns.add(pawnId);
            }
        }
        return pawns;
    }

    private String filterPlace(String noFilteredPlaceId)
    {

        Place place = placeRepo.findPlace(boardId, noFilteredPlaceId);
        if (place != null) {
            return place.getId();
        }

        return "";
    }

    private Map<String, Integer> getValidPositions(Map<String, Integer> positions)
    {
        Map<String, Integer> postionsMap = new HashMap<>();

        for (Map.Entry<String, Integer> entry : positions.entrySet()
                ) {
            String pawnId = entry.getKey();
            Pawn pawn = pawnRepo.findPawn(boardId, pawnId);
            if (pawn != null) {
                postionsMap.put(pawn.getId(), entry.getValue());
            }
        }

        return postionsMap;
    }

    public Pawn findPawn(String pawnId)
    {
        return pawnRepo.findPawn(boardId, pawnId);
    }

    public Place findPlace(String placeId)
    {
        return placeRepo.findPlace(boardId, placeId);
    }

    public void movePawn(Pawn pawn, int move)
    {

        if (positions.containsKey(pawn.getId())) {
            try {
                int newPos = (positions.get(pawn.getId()) + move) % fields.size();
                updatePosition(pawn, newPos);
            } catch (AlreadyExistException e) {
                e.printStackTrace();
            } catch (CannotCreateException e) {
                e.printStackTrace();
            }
        }
    }

    public String getPlaceOfPawn(Pawn pawn)
    {
        for (Field field : fields
                ) {
            if (field.getPawns().contains(pawn.getId()))
                return field.getPlace();
        }
        return "Not on the Board";
    }

    public Pawn addPawn(Pawn pawn) throws AlreadyExistException, CannotCreateException
    {
        pawnRepo.addPawnToBoard(boardId, pawn);
        positions.put(pawn.getId(), 0);
        Field field = fields.get(0);
        if (field != null) field.addPawn(pawn.getId());
        return pawn;
    }

    public Pawn addPawnToPosition(Pawn pawn, int position) throws AlreadyExistException, CannotCreateException
    {
        Field field = fields.get(position);
        if (field != null) {
            pawnRepo.addPawnToBoard(boardId, pawn);
            positions.put(pawn.getId(), position);
            field.addPawn(pawn.getId());
        } else {
            addPawn(pawn);
        }


        return pawn;
    }

    public void throwTheDices(String playerId) throws IOException
    {
        RollForm rollForm1 = new ObjectMapper().readValue(rollDice(boardId,playerId), RollForm.class);
        RollForm rollForm2 = new ObjectMapper().readValue(rollDice(boardId,playerId), RollForm.class);
        Roll roll1 = new Roll(rollForm1.getNumber());
        Roll roll2 = new Roll(rollForm2.getNumber());

        Throw thr = new Throw(roll1,roll2);

        rollRepo.addThrowToPawn(boardId,playerId,thr);

    }


    private String rollDice(String game, String player){
        HttpURLConnection connection = null;

        try{
            YellopagesService diceService = DiceServiceProvider.provide("local");
            URL url = new URL(diceService.getUri()+"dice?game="+game+"&player="+player);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type","application/json");
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null){
                response.append(line);
                response.append('\r');
            }

            reader.close();
            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }

        return "";
    }

    public List<Throw> getThowsOfPawn(String pawnId)
    {
        return rollRepo.findAllPawnOf(boardId,pawnId);
    }

    public void checkTurmOfPlayer(String playerId) throws PlayerNotInturnException
    {

        Player player = new Player();

        HttpURLConnection connection = null;

        try{
            YellopagesService gameService = GameServiceProvider.provide("local");
            URL url = new URL(gameService.getUri()+"games/"+boardId+"/players/turn");

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null){
                response.append(line);
                response.append('\r');
            }

            reader.close();

            Player playerInTurn = new ObjectMapper().readValue(response.toString(),Player.class);

            if( !player.equals(playerInTurn)){
                throw new PlayerNotInturnException();
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new PlayerNotInturnException();
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }


    }

    private class Player{
        private String id;
        private String user;
        private String pawn;
        private boolean ready;
        private String accounts;

        private Player(){}

        public Player(String gameId,String id)
        {
            this.id = "/games/"+gameId+"/players/"+id;
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Player player = (Player) o;

            return id != null ? id.equals(player.id) : player.id == null;

        }

        @Override
        public int hashCode()
        {
            return id != null ? id.hashCode() : 0;
        }
    }
}
