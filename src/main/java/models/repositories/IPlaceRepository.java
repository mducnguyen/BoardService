package models.repositories;

import models.Board;
import models.Place;
import models.repositories.exceptions.AlreadyExistException;
import models.repositories.exceptions.CannotCreateException;

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

    void createPlaceListForBoard(String boardId);
}
