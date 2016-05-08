package main.java.ServiceProvider;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * @author DucNguyenMinh
 * @since 05/05/16
 */
public class ServiceProvider
{
    private static final String YELLOW_SERVICE_URL ="http://172.18.0.5:4567";

    public static YellopagesService getService(String serviceID) {
        try {
            URL url = new URL(YELLOW_SERVICE_URL + "/services/" +serviceID);


            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();

            if(!(200 <= responseCode && responseCode <= 300)){
                return null;
            }

            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null){
                response.append(line);
                response.append('\r');
            }

            reader.close();


            ObjectMapper mapper = new ObjectMapper();
            YellopagesService service = mapper.readValue( response.toString(), YellopagesService.class);
            return  service;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
