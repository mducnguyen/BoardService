package main.java.models.repositories;

import main.java.models.Field;

import java.util.HashMap;
import java.util.Map;

/**
 * @author DucNguyenMinh
 * @since 04/05/16
 */
public class FieldRepository implements IFieldRepository
{
    private final Map<String, Field> fieldsMap;

    public FieldRepository(HashMap<String, Field> fieldsMap)
    {
        this.fieldsMap = fieldsMap;
    }

    @Override
    public Field updateOrCreate(Field field)
    {
        fieldsMap.put(field.getPlace(), field);
        return fieldsMap.get(field.getPlace());
    }
}