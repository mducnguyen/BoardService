package main.java.models.repositories;

import main.java.models.Board;
import main.java.models.Place;
import main.java.models.repositories.exceptions.AlreadyExistException;
import main.java.models.repositories.exceptions.CannotCreateException;

import java.util.List;

/**
 * @author DucNguyenMinh
 * @since 05/05/16
 */
public interface IPlaceRepository extends Repository
{
    List<Place> findAllPlacesOfBoard(String boardId);

    Place findPlace(String boardId, String placeId);

    Place addPlaceToBoard(String boardId, Place place) throws CannotCreateException;

    void createPlaceListForBoard(Board board);
}
