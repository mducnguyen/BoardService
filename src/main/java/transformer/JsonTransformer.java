package transformer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import spark.ResponseTransformer;

import java.io.IOException;

/**
 * @author DucNguyenMinh
 * @since 09/04/16
 */
public class JsonTransformer implements ResponseTransformer
{
    @Override
    public String render(Object data) throws Exception
    {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            return mapper.writeValueAsString(data);
        } catch (IOException e){
            throw new RuntimeException("IOException from a StringWriter? " + e.getMessage());
        }
    }
}
