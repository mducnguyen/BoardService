package main.java.models.repositories;

import java.util.HashMap;
import java.util.Map;

/**
 * @author DucNguyenMinh
 * @since 04/05/16
 */
public class RepositoryProvider
{
    private static Map<Class, Repository> repoMapping = new HashMap<>();

    public static void register(Class repositoryClass,Repository repository){
        repoMapping.put(repositoryClass,repository);
    }

    public static Repository getRepository(Class repositoryClasse){
        return repoMapping.get(repositoryClasse);
    }
}
