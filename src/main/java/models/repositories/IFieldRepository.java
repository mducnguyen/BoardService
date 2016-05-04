package main.java.models.repositories;

import main.java.models.Field;
import main.java.models.repositories.Repository;

/**
 * @author DucNguyenMinh
 * @since 04/05/16
 */
public interface IFieldRepository extends Repository
{
    Field updateOrCreate(Field field);
}
