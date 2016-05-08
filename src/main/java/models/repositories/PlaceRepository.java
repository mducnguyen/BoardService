package main.java.models.repositories;

import main.java.models.Board;
import main.java.models.Place;
import main.java.models.repositories.exceptions.AlreadyExistException;
import main.java.models.repositories.exceptions.CannotCreateException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DucNguyenMinh
 * @since 05/05/16
 */
public class PlaceRepository implements IPlaceRepository
{
    private Map<String, Map<String,Place>> placeMap;

    public PlaceRepository(Map<String, Map<String, Place>> placeMap)
    {
        this.placeMap = placeMap;
    }

    private long counter;

    @Override
    public List<Place> findAllPlacesOfBoard(String boardId)
    {
        List<Place> places = new ArrayList<>();
        if (placeMap.get(boardId) != null) {
            for (Map.Entry<String, Place> entry :
                    placeMap.get(boardId).entrySet()) {
                places.add(entry.getValue());
            }
        }
        return places;
    }

    @Override
    public Place findPlace(String boardId, String placeId)
    {
        Map<String, Place> boardPlaceMap = placeMap.get(boardId);
        if(boardPlaceMap != null){
            return boardPlaceMap.get(placeId);
        }
        return null;
    }

    @Override
    public Place addPlaceToBoard(String boardId, Place place) throws CannotCreateException
    {
        Map<String, Place> gamePlaceMap = placeMap.get(boardId);
        if(gamePlaceMap == null){
            throw new CannotCreateException();
        }
        place.setId(counter++);
        gamePlaceMap.put(""+place.getId(),place);

        return place;
    }

    @Override
    public void createPlaceListForBoard(String board)
    {
        if (!placeMap.containsKey(board)) {
            placeMap.put(board, new HashMap<>());
        }
    }
}
